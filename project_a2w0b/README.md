# Java Chess Clone

##### 2020W1 CPSC 210 project


My project will be a console-based implementation of chess.
The application will mimic possible player actions that would occur in a real-life game
including moving and capturing pieces. The pieces will be represented by single characters, with
lowercase representing black and uppercase representing white. The board will be represented
by a character matrix, with unoccupied squares represented by 'O'. For reference:

* K and k = king
* Q and q = queen
* R and r = rook
* B and b = bishop
* N and n = knight
* P and p = pawn

Board coordinates are defined such that (0,0) is the starting position of White's queenside
rook and (7,7) is the starting position of Black's kingside rook. Moves will be made
by entering the coordinates of a piece followed by the coordinates of the destination
square.


The application will be useful for anyone interested in playing chess 
 In particular, this application is perfect for people who don't have a physical 
chess set and don't want to buy one.

I chose to do this project because I enjoy the game of chess and see this a 
good learning experience to find an efficient way to implement and test 
this game using object-oriented design. 

# User Stories

1. As a user, I want to be able to move my pieces according to their type
2. As a user, I want to be able to capture my opponent's pieces and add them to my
collection so that my total material is calculated at the start of each turn
3. As a user, I want to get warned when I try to make an invalid move
4. As a user, I want to notify my opponent when I have put their king in check
5. As a user, I want to have the option to save the board state when I quit the game
6. As a user, I want to have to option to see the last saved board state when I start the program

# Phase 4: Task 2

For task two, I have chosen to implement a type hierarchy.
The type hierarchy involves the classes related to
the chess pieces. Piece is an abstract class which contains the general data and behavior
for a chess piece. Piece is extended by classes representing specific pieces (Pawn, Knight, Bishop, Rook, Queen, and King).
These classes contain information on how each piece can move and capture other pieces. The methods that are 
overridden differently for each subclass of Piece are getValue() and hasValidPath().

# Phase 4: Task 3 
 
Looking at The current UML Diagram for my project, I would refactor my code in the 
following ways to improve its structure and readability:
- Making the abstract Piece class have a field of type Type rather than specific piece classes
having a field of type Type would reduce code duplication
- Splitting up the GUI class into a smaller class for each major user interface component would
increase cohesion (e.g. extracting a ChessBoard class that only deals with data and behavior
related to the board).
- Extracting out movement-related methods that could be shared by multiple pieces (e.g. diagonal (shared by Bishop and
Queen), vertical (shared by Rook and Queen), and horizontal (shared by Rook and Queen) movement methods could be 
implemented in Piece)