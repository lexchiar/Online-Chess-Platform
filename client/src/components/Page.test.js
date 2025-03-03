/**
 * Tests for Page.js
 * Author: ahuss12
 * Date: 11/21/2024
 */

import React from 'react';
import { render, screen, fireEvent } from '@testing-library/react';
import '@testing-library/jest-dom';
import Page from './Page';

// Mock the `useServerSettings` and `useGuest` hooks
jest.mock('../hooks/useServerSettings', () => ({
    useServerSettings: () => [jest.fn(), jest.fn()],
}));

jest.mock('../hooks/useGuest', () => ({
    useGuest: () => ({
        guest: jest.fn(),
        guestActions: jest.fn(),
        makeGuestRequest: jest.fn().mockResolvedValue({ validity: true }),
    }),
}));

describe('ahuss12 - Page Component Tests', () => {
    const mockShowMessage = jest.fn();

    beforeEach(() => {
        mockShowMessage.mockClear();
    });

    test('ahuss12 - renders Login page by default', () => {
        render(<Page showMessage={mockShowMessage} />);
        expect(screen.getByText('Login Page')).toBeInTheDocument();
        expect(screen.getByPlaceholderText('Username')).toBeInTheDocument();
        expect(screen.getByPlaceholderText('Password')).toBeInTheDocument();
    });

    test('ahuss12 - toggles password visibility on Signup page', () => {
        render(<Page showMessage={mockShowMessage} />);
        fireEvent.click(screen.getByText('Signup')); // Switch to Signup page
        expect(screen.getByText('Signup Page')).toBeInTheDocument();

        const passwordInput = screen.getByPlaceholderText('Password');
        const toggleButton = screen.getAllByText('Show')[0];

        expect(passwordInput).toHaveAttribute('type', 'password');
        fireEvent.click(toggleButton); // Toggle visibility
        expect(passwordInput).toHaveAttribute('type', 'text');
        fireEvent.click(toggleButton); // Toggle back
        expect(passwordInput).toHaveAttribute('type', 'password');
    });

    test('ahuss12 - renders Forgot Password page', () => {
        render(<Page showMessage={mockShowMessage} />);
        fireEvent.click(screen.getByText('Forgot Password')); // Switch to Forgot Password page

        expect(screen.getByText('Forgot Password')).toBeInTheDocument();
        expect(screen.getByPlaceholderText('Username or Email')).toBeInTheDocument();
    });

    test('ahuss12 - handles Guest mode navigation to Game page', () => {
        render(<Page showMessage={mockShowMessage} />);
        fireEvent.click(screen.getByText('Continue as Guest')); // Switch to Game page

        expect(screen.getByText('Gameplay Area')).toBeInTheDocument();
    });

    /*test('ahuss12 - renders chessboard with initial pieces', () => {
        render(<Page showMessage={mockShowMessage} />);
        fireEvent.click(screen.getByText('Continue as Guest')); // Switch to Game page

        // Verify some initial chess pieces
        expect(screen.getByText('♙')).toBeInTheDocument(); // White pawn
        expect(screen.getByText('♜')).toBeInTheDocument(); // Black rook
    });*/

    /*test('ahuss12 - highlights valid moves when selecting a piece', async () => {
        render(<Page showMessage={mockShowMessage} />);
        fireEvent.click(screen.getByText('Continue as Guest')); // Switch to Game page

        const piece = screen.getByText('♙'); // Select a white pawn
        fireEvent.click(piece);

        // Since valid moves are calculated asynchronously, you can verify they appear
        expect(mockShowMessage).not.toHaveBeenCalled(); // Ensure no errors
    });*/
});
