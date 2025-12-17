# ♟️ Chess (Java)

A **pure Java implementation of Chess** focused on clean object-oriented design and accurate main.java.chess.move generation.

This project models the core rules of chess (pieces, main.java.chess.board, moves, legality checks) and is structured to be easily extended with a UI, AI engine, or networking layer later.

---

## 🧠 Project Goals

* Practice **object-oriented design** (SOLID principles)
* Implement **correct chess rules** and legal main.java.chess.move generation
* Keep the core logic **UI-agnostic**
* Build a strong foundation for future features (GUI, engine, multiplayer)

---

## ✨ Features

* Full main.java.chess.board representation (`main.java.chess.board.Board`, `main.java.chess.board.Square`)
* All chess pieces implemented:
    * main.java.chess.piece.King
    * main.java.chess.piece.Queen
    * main.java.chess.piece.Rook
    * main.java.chess.piece.Bishop
    * main.java.chess.piece.Knight
    * main.java.chess.piece.Pawn
* Centralized **main.java.chess.move generation**
* main.java.chess.move.Move validation and illegal main.java.chess.move handling
* Explicit modeling of:
    * main.java.chess.move.Move types
    * main.java.chess.piece.Piece types
    * Colors
* Custom exception for illegal moves

---

## 📂 Project Structure

```
chess/
├── src/
│   ├── main.java.Main.java              # Application entry point
│   ├── main.java.chess.board.Board.java             # main.java.chess.board.Board state and main.java.chess.game logic
│   ├── main.java.chess.board.Square.java            # main.java.chess.board.Board squares
│   ├── main.java.chess.piece.Piece.java             # Abstract main.java.chess.piece base class
│   ├── main.java.chess.piece.Pawn.java
│   ├── main.java.chess.piece.Rook.java
│   ├── main.java.chess.piece.Knight.java
│   ├── main.java.chess.piece.Bishop.java
│   ├── main.java.chess.piece.Queen.java
│   ├── main.java.chess.piece.King.java
│   ├── main.java.chess.piece.PieceType.java         # Enum of main.java.chess.piece types
│   ├── main.java.chess.piece.Color.java             # main.java.chess.piece.Piece color enum
│   ├── main.java.chess.move.Move.java              # Domain main.java.chess.move object
│   ├── main.java.chess.move.MoveDto.java           # main.java.chess.move.Move data transfer object
│   ├── main.java.chess.move.MoveGenerator.java     # Legal main.java.chess.move generation
│   ├── main.java.chess.move.MoveType.java          # Enum of main.java.chess.move types
│   └── main.java.chess.move.IllegalMoveException.java
├── .gitignore
├── chess.iml
└── README.md
```

---

## 🚀 Getting Started

### Requirements

* **Java JDK 21**
* IntelliJ IDEA (recommended) or any Java IDE

---

### Clone the Repository

```bash
git clone https://github.com/LucasPierru/chess.git
cd chess
```

---

### Run the Project

Using the command line:

```bash
javac src/*.java
java main.java.Main
```

Or simply run `main.java.Main.java` from your IDE.

---

## 🧩 Design Overview

### main.java.chess.board.Board & Squares

* `main.java.chess.board.Board` owns the state of the main.java.chess.game
* `main.java.chess.board.Board` contains an 8x8 array of `main.java.chess.piece.Piece`

### Pieces

* All pieces extend the abstract `main.java.chess.piece.Piece` class
* main.java.chess.piece.Piece-specific movement logic is encapsulated per class

### main.java.chess.move.Move Generation

* `main.java.chess.move.MoveGenerator` computes and validates **legal moves**
* `main.java.chess.move.Move` represents a validated domain action
* `main.java.chess.move.MoveDto` can be used for UI / external input

### Error Handling

* `main.java.chess.move.IllegalMoveException` is thrown when invalid moves are attempted

---

## 🔮 Future Improvements

* Checkmate detection
* FEN import/export
* Graphical UI (Web interface)
* Chess engine (minimax / Stockfish integration)
* Online or local multiplayer

---

## 🧪 Testing

Currently manual testing via execution.

Planned:

* JUnit tests for main.java.chess.move generation
* Regression tests for edge cases

---

## 📜 License

This project is open-source. Add a license if you plan to distribute it.

---

## 👤 Author

**Lucas Pierru**

GitHub: [https://github.com/LucasPierru](https://github.com/LucasPierru)

---

> ♟️ *This project focuses on correctness and clean architecture over UI — a solid base for any chess-related application.*
