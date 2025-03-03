import React, { useState } from 'react';
import { useServerSettings } from '../hooks/useServerSettings';
import { useGuest } from '../hooks/useGuest';
import { sendAPIRequest } from '../utils/restfulAPI';


export default function Page(props) {
    const [serverSettings, processServerConfigSuccess] = useServerSettings(props.showMessage);
    const [boardState, setBoardState] = useState(initializeBoard());
    const [selectedPiece, setSelectedPiece] = useState(null);
    const [validMoves, setValidMoves] = useState([]);
    const [movesList, setMovesList] = useState([]);
    const [capturedWhitePieces, setCapturedWhitePieces] = useState([]);
    const [capturedBlackPieces, setCapturedBlackPieces] = useState([]);
    const [currentTurn, setCurrentTurn] = useState('w'); // 'w' for white, 'b' for black

    // New state variables for authentication and navigation
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [confirmPassword, setConfirmPassword] = useState('');
    const [currentPage, setCurrentPage] = useState('login');
    const [showPassword, setShowPassword] = useState(false); 
    //api jargon
    const { guest, guestActions, makeGuestRequest } = useGuest();

    // Styles for input fields and buttons
    const inputStyle = {
        padding: '10px',
        fontSize: '16px',
        width: '250px',
        marginBottom: '10px',
        border: '1px solid #FFD700',
        borderRadius: '5px',
        backgroundColor: '#ffffff',
        color: '#000000',
    };

    const buttonStyle = {
        padding: '10px 20px',
        fontSize: '16px',
        cursor: 'pointer',
        backgroundColor: '#FFD700',
        color: '#376746',
        border: 'none',
        borderRadius: '5px',
    };

    class ChessCoordinate {
        constructor(row, col) {
            this.x = row;
            this.y = col;
        }
    }

    class Piece {
        constructor(color, type, location) {
            this.color = color;
            switch (type) {
                case 'K':
                    this.type = 'KING'
                    break;
                case 'Q':
                    this.type = 'QUEEN'
                    break;
                case 'P':
                    this.type = 'PAWN'
                    break;
                case 'R':
                    this.type = 'ROOK'
                    break;
                case 'B':
                    this.type = 'BISHOP'
                    break;
                case 'N':
                    this.type = 'KNIGHT'
                    break;
            }
            this.location = location;
        }
    }

    function convertToPieceObjects(board) {
        const pieceBoard = board.map((row, rowIndex) =>
            row.map((pieceNotation, colIndex) => {
                if (pieceNotation) {
                    const color = pieceNotation[0] === 'w' ? 'WHITE' : 'BLACK';
                    const type = pieceNotation[1];
                    // Don't flip the coordinates here
                    const location = new ChessCoordinate(rowIndex, colIndex);
                    return new Piece(color, type, location);
                }
                return null;
            })
        );
        return pieceBoard;
    }

    // Initialize the chessboard with pieces in starting positions
    function initializeBoard() {
        const emptyRow = new Array(8).fill(null);
        return [
            ['bR', 'bN', 'bB', 'bQ', 'bK', 'bB', 'bN', 'bR'],
            ['bP', 'bP', 'bP', 'bP', 'bP', 'bP', 'bP', 'bP'],
            [...emptyRow],
            [...emptyRow],
            [...emptyRow],
            [...emptyRow],
            ['wP', 'wP', 'wP', 'wP', 'wP', 'wP', 'wP', 'wP'],
            ['wR', 'wN', 'wB', 'wQ', 'wK', 'wB', 'wN', 'wR'],
        ];
    }

    // Map piece codes to Unicode chess symbols
    function getPieceUnicode(piece) {
        const pieceUnicode = {
            wK: '♔',
            wQ: '♕',
            wR: '♖',
            wB: '♗',
            wN: '♘',
            wP: '♙',
            bK: '♚',
            bQ: '♛',
            bR: '♜',
            bB: '♝',
            bN: '♞',
            bP: '♟︎',
        };
        return pieceUnicode[piece] || null;
    }

    // Convert board coordinates to algebraic notation
    function getPositionNotation(row, col) {
        const files = ['a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'];
        return files[col] + (8 - row);
    }

    // Move a piece from one square to another
    async function movePiece(fromRow, fromCol, toRow, toCol) {
        const newBoard = boardState.map(row => row.slice()); // Deep copy
        const movingPiece = newBoard[fromRow][fromCol];

        // Check if it's the correct player's turn
        if (movingPiece[0] !== currentTurn) { return props.showMessage("It's not your turn!", "error"); }

        const targetPiece = newBoard[toRow][toCol];

        // Check if there's a piece to capture
        if (targetPiece) {
            targetPiece[0] === 'w'
                ? setCapturedWhitePieces(prev => [...prev, targetPiece])
                : targetPiece[0] === 'b' && setCapturedBlackPieces(prev => [...prev, targetPiece]);
        }

        // Trigger the guest request
        const response = await makeGuestRequest(
            convertToPieceObjects(newBoard)[fromRow][fromCol],
            { x: toCol, y: toRow },
            convertToPieceObjects(newBoard),
            serverSettings.serverUrl
        );
        // // Handle response if needed
        // if (response.validity) {
        //     // Do something
        //     // If false, revert board state
        //     // If true proceed with updating board
        // }

        newBoard[toRow][toCol] = movingPiece;
        newBoard[fromRow][fromCol] = null;
        setBoardState(newBoard);

        // Update the moves list
        setMovesList(prevMoves => [...prevMoves,
        `${getPositionNotation(fromRow, fromCol)} to ${getPositionNotation(toRow, toCol)}`
        ]);

        setCurrentTurn(currentTurn === 'w' ? 'b' : 'w'); // Switch turns
        setSelectedPiece(null); // Reset selected piece and valid moves
        setValidMoves([]);
    }

    // Calculate valid moves for a selected piece
    async function calculateValidMoves(piece, row, col, boardState) {
        console.log('Calculating moves for piece:', piece, 'at row:', row, 'col:', col);

        const requestBody = {
            requestType: "validateMoves",
            piece: new Piece(piece[0] === 'w' ? 'WHITE' : 'BLACK', piece[1], new ChessCoordinate(col, 7 - row)),
            board: convertToPieceObjects(boardState)
        };

        try {
            console.log('Sending request:', requestBody);
            const response = await sendAPIRequest(requestBody, serverSettings.serverUrl);
            console.log('Received response:', response);
            return response?.validMoves?.map(move => ({ row: 7 - move.y, col: move.x })) || [];
        } catch (error) {
            console.error('API Error:', error);
            props.showMessage(`Error calculating valid moves: ${error.message}`, "error");
            return [];
        }
    }

    // Check if a move is valid
    function isMoveValid(row, col) {
        return validMoves.some(
            move => move.row === row && move.col === col
        );
    }

    // Chessboard component
    const Chessboard = ({
        boardState,
        selectedPiece,
        setSelectedPiece,
        validMoves,
        setValidMoves,
    }) => {
        // Handle clicks on squares
        async function handleSquareClick(row, col) {
            const piece = boardState[row][col];
            if (selectedPiece) {
                if (isMoveValid(row, col)) {
                    movePiece(
                        selectedPiece.row,
                        selectedPiece.col,
                        row,
                        col
                    );
                } else {
                    setSelectedPiece(null);
                    setValidMoves([]);
                }
            } else if (piece && piece[0] === currentTurn) {
                setSelectedPiece({ row, col });
                const moves = await calculateValidMoves(piece, row, col, boardState);
                console.log('Setting valid moves:', moves);
                setValidMoves(moves);
            }
        }

        function renderSquare(row, col) {
            const isBlackSquare = (row + col) % 2 === 1;
            const piece = boardState[row][col];
            const isSelected = selectedPiece && selectedPiece.row === row && selectedPiece.col === col;
            const isValidMove = validMoves.some(move => move.row === row && move.col === col);
            const backgroundColor = isSelected ? 'yellow' : isValidMove ? 'lightgreen' : (isBlackSquare ? '#769656' : '#eeeed2');

            const squareStyle = {
                width: '60px', height: '60px', backgroundColor,
                display: 'flex', justifyContent: 'center', alignItems: 'center',
                fontSize: '36px', cursor: 'pointer'
            };

            return (
                <div key={`${row}-${col}`} onClick={() => handleSquareClick(row, col)} style={squareStyle}>
                    {piece && getPieceUnicode(piece)}
                </div>
            );
        }

        // Create the chessboard grid
        const squares = [];
        for (let row = 0; row < 8; row++) {
            for (let col = 0; col < 8; col++) {
                squares.push(renderSquare(row, col));
            }
        }

        return (
            <div
                style={{
                    display: 'grid',
                    gridTemplateColumns: 'repeat(8, 60px)',
                    gridTemplateRows: 'repeat(8, 60px)',
                    border: '2px solid black',
                }}
            >
                {squares}
            </div>
        );
    };

    const handleSignup = async () => {
        if (!username || !password || !confirmPassword) { return props.showMessage('Please fill in all fields.', 'error'); }
        if (password !== confirmPassword) { return props.showMessage('Passwords do not match.', 'error'); }

        try {
            const response = await fetch(`${serverUrl}/api/signup`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ username, password })
            });

            if (response.ok) {
                props.showMessage('Signup successful!', 'success');
                setCurrentPage('login');
            } else {
                const data = await response.json();
                props.showMessage(data.message || 'Signup failed.', 'error');
            }
        } catch (error) {
            console.error('Error during signup:', error);
            props.showMessage('Signup failed.', 'error');
        }
    };

    // Handle Login
    const handleLogin = async () => {
        if (!username || !password) { return props.showMessage('Please enter username and password.', 'error'); }

        try {
            const response = await fetch(`${serverUrl}/api/login`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ username, password })
            });

            if (response.ok) {
                props.showMessage('Login successful!', 'success');
                setCurrentPage('game');
            } else {
                const data = await response.json();
                props.showMessage(data.message || 'Login failed.', 'error');
            }
        } catch (error) {
            console.error('Error during login:', error);
            props.showMessage('Login failed.', 'error');
        }
    };

    // Handle Forgot Password
    const handleForgotPassword = () => {
        // Implement forgot password logic here
        // For now, display a message
        props.showMessage('Password reset link sent!', 'info');
        setCurrentPage('login');
    };

    // Render Functions
    const renderLoginPage = () => (
        <div
            style={{
                backgroundColor: '#376746',
                height: '100vh',
                display: 'flex',
                flexDirection: 'column',
                alignItems: 'center',
                justifyContent: 'center',
                color: '#FFD700',
            }}
        >
            <h1 style={{ fontSize: '48px', marginBottom: '20px' }}>Chess-Game t15</h1>
            <h2 style={{ marginBottom: '30px' }}>Login Page</h2>
            <div style={{ marginBottom: '20px' }}>
                <input
                    type="text"
                    placeholder="Username"
                    value={username}
                    onChange={e => setUsername(e.target.value)}
                    style={inputStyle}
                />
                <br />
                <input
                    type="password"
                    placeholder="Password"
                    value={password}
                    onChange={e => setPassword(e.target.value)}
                    style={inputStyle}
                />
            </div>
            <button
                onClick={() => setCurrentPage('game')}
                style={buttonStyle}
            >
                Continue as Guest
            </button>
            <div style={{ marginTop: '20px' }}>
                <a
                    href="#"
                    onClick={() => setCurrentPage('signup')}
                    style={{ color: '#FFD700', textDecoration: 'underline', marginRight: '10px' }}
                >
                    Signup
                </a>
                |
                <a
                    href="#"
                    onClick={() => setCurrentPage('forgotPassword')}
                    style={{ color: '#FFD700', textDecoration: 'underline', marginLeft: '10px' }}
                >
                    Forgot Password
                </a>
            </div>
        </div>
    );

    const renderSignupPage = () => (
        <div
            style={{
                backgroundColor: '#376746',
                height: '100vh',
                display: 'flex',
                flexDirection: 'column',
                alignItems: 'center',
                justifyContent: 'center',
                color: '#FFD700',
            }}
        >
            <h1 style={{ fontSize: '48px', marginBottom: '20px' }}>Chess-Game t15</h1>
            <h2 style={{ marginBottom: '30px' }}>Signup Page</h2>
            <div style={{ marginBottom: '20px' }}>
                <input
                    type="text"
                    placeholder="Username"
                    value={username}
                    onChange={(e) => setUsername(e.target.value)}
                    style={inputStyle}
                />
                <br />
                <div style={{ position: 'relative' }}>
                    <input
                        type={showPassword ? 'text' : 'password'}
                        placeholder="Password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        style={inputStyle}
                    />
                    <button
                        onClick={() => setShowPassword(!showPassword)}
                        style={{
                            position: 'absolute',
                            right: '10px',
                            top: '50%',
                            transform: 'translateY(-50%)',
                            background: 'none',
                            border: 'none',
                            cursor: 'pointer',
                            color: '#FFD700',
                            fontSize: '14px',
                        }}
                    >
                        {showPassword ? 'Hide' : 'Show'}
                    </button>
                </div>
                <br />
                <div style={{ position: 'relative' }}>
                    <input
                        type={showPassword ? 'text' : 'password'}
                        placeholder="Confirm Password"
                        value={confirmPassword}
                        onChange={(e) => setConfirmPassword(e.target.value)}
                        style={inputStyle}
                    />
                    <button
                        onClick={() => setShowPassword(!showPassword)}
                        style={{
                            position: 'absolute',
                            right: '10px',
                            top: '50%',
                            transform: 'translateY(-50%)',
                            background: 'none',
                            border: 'none',
                            cursor: 'pointer',
                            color: '#FFD700',
                            fontSize: '14px',
                        }}
                    >
                        {showPassword ? 'Hide' : 'Show'}
                    </button>
                </div>
            </div>
            <button onClick={handleSignup} style={buttonStyle}>
                Signup
            </button>
            <div style={{ marginTop: '20px' }}>
                <a
                    href="#"
                    onClick={() => setCurrentPage('login')}
                    style={{ color: '#FFD700', textDecoration: 'underline' }}
                >
                    Back to Login
                </a>
            </div>
        </div>
    );
    const renderForgotPasswordPage = () => (
        <div
            style={{
                backgroundColor: '#376746',
                height: '100vh',
                display: 'flex',
                flexDirection: 'column',
                alignItems: 'center',
                justifyContent: 'center',
                color: '#FFD700',
            }}
        >
            <h1 style={{ fontSize: '48px', marginBottom: '20px' }}>Chess-Game t15</h1>
            <h2 style={{ marginBottom: '30px' }}>Forgot Password</h2>
            <div style={{ marginBottom: '20px' }}>
                <input
                    type="text"
                    placeholder="Username or Email"
                    value={username}
                    onChange={e => setUsername(e.target.value)}
                    style={inputStyle}
                />
            </div>
            <button
                onClick={handleForgotPassword}
                style={buttonStyle}
            >
                Reset Password
            </button>
            <div style={{ marginTop: '20px' }}>
                <a
                    href="#"
                    onClick={() => setCurrentPage('login')}
                    style={{ color: '#FFD700', textDecoration: 'underline' }}
                >
                    Back to Login
                </a>
            </div>
        </div>
    );

    const renderGamePage = () => (
        <div>
            <h1
                style={{
                    textAlign: 'center',
                    marginTop: '20px',
                }}
            >
                Gameplay Area
            </h1>
            <div style={{ textAlign: 'center', marginTop: '20px', fontSize: '18px', fontWeight: 'bold' }}>
                Current Turn: {currentTurn === 'w' ? 'White' : 'Black'}
            </div>
            <div
                style={{
                    display: 'flex',
                    justifyContent: 'center',
                    alignItems: 'flex-start',
                    marginTop: '40px',
                }}
            >
                {/* Left Column for Moves */}
                <div
                    style={{
                        width: '200px',
                        marginRight: '20px',
                        border: '1px solid #ccc',
                        padding: '10px',
                        height: '480px',
                        overflowY: 'auto',
                    }}
                >
                    <h3 style={{ textAlign: 'center' }}>
                        Moves
                    </h3>
                    <ul>
                        {movesList.map((move, index) => (
                            <li key={index}>{move}</li>
                        ))}
                    </ul>
                </div>

                {/* Chessboard */}
                <Chessboard
                    boardState={boardState}
                    selectedPiece={selectedPiece}
                    setSelectedPiece={setSelectedPiece}
                    validMoves={validMoves}
                    setValidMoves={setValidMoves}
                />

                {/* Right Column for Player History and Stats */}
                <div
                    style={{
                        width: '200px',
                        marginLeft: '20px',
                        border: '1px solid #ccc',
                        padding: '10px',
                        height: '480px',
                        overflowY: 'auto',
                    }}
                >
                    <h3 style={{ textAlign: 'center' }}>
                        Player History
                    </h3>
                    <ul>
                        <li>Games Played: 10</li>
                        <li>Wins: 6</li>
                        <li>Losses: 4</li>
                        {/* Add more stats as needed */}
                    </ul>
                </div>
            </div>

            {/* Captured Pieces */}
            <div
                style={{
                    display: 'flex',
                    justifyContent: 'center',
                    alignItems: 'center',
                    marginTop: '20px',
                }}
            >
                {/* Captured Black Pieces */}
                <div
                    style={{
                        display: 'flex',
                        marginRight: '50px',
                    }}
                >
                    <h3 style={{ marginRight: '10px' }}>
                        Captured Black Pieces:
                    </h3>
                    <div style={{ display: 'flex' }}>
                        {capturedBlackPieces.map(
                            (piece, index) => (
                                <div
                                    key={index}
                                    style={{
                                        fontSize: '24px',
                                        marginRight: '5px',
                                    }}
                                >
                                    {getPieceUnicode(piece)}
                                </div>
                            )
                        )}
                    </div>
                </div>

                {/* Captured White Pieces */}
                <div
                    style={{
                        display: 'flex',
                    }}
                >
                    <h3 style={{ marginRight: '10px' }}>
                        Captured White Pieces:
                    </h3>
                    <div style={{ display: 'flex' }}>
                        {capturedWhitePieces.map(
                            (piece, index) => (
                                <div
                                    key={index}
                                    style={{
                                        fontSize: '24px',
                                        marginRight: '5px',
                                    }}
                                >
                                    {getPieceUnicode(piece)}
                                </div>
                            )
                        )}
                    </div>
                </div>
            </div>
        </div>
    );

    // Main render
    return (
        <div>
            {currentPage === 'login' && renderLoginPage()}
            {currentPage === 'signup' && renderSignupPage()}
            {currentPage === 'forgotPassword' && renderForgotPasswordPage()}
            {currentPage === 'game' && renderGamePage()}
        </div>
    );
}
