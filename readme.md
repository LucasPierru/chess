# ♟️ Chess (Java)

A **pure Java implementation of Chess** focused on clean object-oriented design and accurate move generation.

This project models the core rules of chess (pieces, board, moves, legality checks) and is structured to be easily extended with a UI, AI engine, or networking layer later.

---

## 🧠 Project Goals

* Practice **object-oriented design** (SOLID principles)
* Implement **correct chess rules** and legal move generation
* Keep the core logic **UI-agnostic**
* Build a strong foundation for future features (GUI, engine, multiplayer)

---

## ✨ Features

* Full board representation (`board.Board`, `board.Square`)
* All chess pieces implemented:
    * piece.King
    * piece.Queen
    * piece.Rook
    * piece.Bishop
    * piece.Knight
    * piece.Pawn
* Centralized **move generation**
* move.Move validation and illegal move handling
* Explicit modeling of:
    * move.Move types
    * piece.Piece types
    * Colors
* Custom exception for illegal moves

---

## 📂 Project Structure

```
chess/
├── src/
│   ├── Main.java              # Application entry point
│   ├── board.Board.java             # board.Board state and game logic
│   ├── board.Square.java            # board.Board squares
│   ├── piece.Piece.java             # Abstract piece base class
│   ├── piece.Pawn.java
│   ├── piece.Rook.java
│   ├── piece.Knight.java
│   ├── piece.Bishop.java
│   ├── piece.Queen.java
│   ├── piece.King.java
│   ├── piece.PieceType.java         # Enum of piece types
│   ├── piece.Color.java             # piece.Piece color enum
│   ├── move.Move.java              # Domain move object
│   ├── move.MoveDto.java           # move.Move data transfer object
│   ├── move.MoveGenerator.java     # Legal move generation
│   ├── move.MoveType.java          # Enum of move types
│   └── move.IllegalMoveException.java
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
java Main
```

Or simply run `Main.java` from your IDE.

---

## 🧩 Design Overview

### board.Board & Squares

* `board.Board` owns the state of the game
* `board.Board` contains an 8x8 array of `piece.Piece`

### Pieces

* All pieces extend the abstract `piece.Piece` class
* piece.Piece-specific movement logic is encapsulated per class

### move.Move Generation

* `move.MoveGenerator` computes and validates **legal moves**
* `move.Move` represents a validated domain action
* `move.MoveDto` can be used for UI / external input

### Error Handling

* `move.IllegalMoveException` is thrown when invalid moves are attempted

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

* JUnit tests for move generation
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
