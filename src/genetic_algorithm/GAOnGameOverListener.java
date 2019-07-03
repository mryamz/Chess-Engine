package genetic_algorithm;

import chess_engine.structs.OnGameCompleteListener;

public class GAOnGameOverListener implements OnGameCompleteListener {

	private boolean running = true;
	private int finish_state;

	@Override
	public void OnGameComplete(int state) {
		running = false;
		finish_state = state;
	}

	public boolean gameInSession() {
		return running;
	}

	public int getFinish_state() {
		return finish_state;
	}
}
