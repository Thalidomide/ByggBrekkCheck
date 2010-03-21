package no.teamjava.byggbrekker.logic;

import no.teamjava.byggbrekker.entities.Settings;

/**
 * @author Olav Jensen, Raymond Koteng
 * @since 21.okt.2009
 */
public class BuildChecker extends Thread {

	private boolean run = true;
	private CheckerListener listener;

	public BuildChecker(CheckerListener listener) {
		this.listener = listener;
	}

	@Override
	public void run() {
		long lastUpdate = 0;

		while (run) {
			if (System.currentTimeMillis() - lastUpdate >= Settings.CHECK_INTERVAL || lastUpdate == 0) {
				listener.updateTimeToUpdate(0);
				listener.updateStatus();
				lastUpdate = System.currentTimeMillis();
			} else {
				long timeSinceLastUpdate = System.currentTimeMillis() - lastUpdate;
				long timeToNextUpdate = Settings.CHECK_INTERVAL - timeSinceLastUpdate;
				listener.updateTimeToUpdate(timeToNextUpdate);
			}

			try {
				sleep(Settings.UPDATE_TIME_TO_CHECK_INTERVAL);
			} catch (InterruptedException e) {
				run = false;
			}
		}
	}

	public void stopChecking() {
		run = false;
		interrupt();
		listener.stop();
	}
}
