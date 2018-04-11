package com.anami.yu;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class QbAdapter {

	public static void main(String[] args) {

		Logger logger = LoggerFactory.getLogger(QbAdapter.class);
		int pause = 1000 * Integer.valueOf(System.getenv("pause_seconds"));

		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");

		Batch batch = new Batch(ctx);

		for (;;) {
			try {
				batch.run();
				logger.info("Waiting for next query ---");
				Thread.sleep(pause);
			} catch (Exception e) {
				logger.info("Running job encountered exception -", e);
			}
		}

		// ctx.close();

	}
}
