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

* Full board representation (`Board`, `Square`)
* All chess pieces implemented:
    * King
    * Queen
    * Rook
    * Bishop
    * Knight
    * Pawn
* Centralized **move generation**
* Move validation and illegal move handling
* Explicit modeling of:
    * Move types
    * Piece types
    * Colors
* Custom exception for illegal moves

---

## 📂 Project Structure

```
chess/
├── src/
│   ├── Main.java              # Application entry point
│   ├── Board.java             # Board state and game logic
│   ├── Square.java            # Board squares
│   ├── Piece.java             # Abstract piece base class
│   ├── Pawn.java
│   ├── Rook.java
│   ├── Knight.java
│   ├── Bishop.java
│   ├── Queen.java
│   ├── King.java
│   ├── Move.java              # Domain move object
│   ├── MoveDto.java           # Move data transfer object
│   ├── MoveGenerator.java     # Legal move generation
│   ├── MoveType.java          # Enum of move types
│   ├── PieceType.java         # Enum of piece types
│   ├── Color.java             # Piece color enum
│   └── IllegalMoveException.java
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

### Board & Squares

* `Board` owns the state of the game
* `Board` contains an 8x8 array of `Piece`

### Pieces

* All pieces extend the abstract `Piece` class
* Piece-specific movement logic is encapsulated per class

### Move Generation

* `MoveGenerator` computes and validates **legal moves**
* `Move` represents a validated domain action
* `MoveDto` can be used for UI / external input

### Error Handling

* `IllegalMoveException` is thrown when invalid moves are attempted

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
