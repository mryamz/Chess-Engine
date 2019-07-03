package chess_engine.structs;

import chess_engine.entities.BoardElement;

public interface OnValidMovePlayedListener {

	void move(BoardElement element);

}
