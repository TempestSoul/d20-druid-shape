package com.tempestsoul.dnd.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import com.tempestsoul.dnd.d20.model.Creature;
import com.tempestsoul.dnd.service.parser.ShapeListParser;

public class CsvShapeParser implements ShapeLoadService {

	private static final Logger logger = Logger.getLogger(CsvShapeParser.class.getSimpleName());

	private ShapeListParser parser = new ShapeListParser();

	@Override
	public List<Creature> loadShapes() {
		List<Creature> shapes = null;
		
		File shapeFile = new File("src/main/resources/druid shapes.csv");
		FileReader fileReader = null;
		try {
			fileReader = new FileReader(shapeFile);
			shapes = parser.parseCreatureFile(fileReader);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			if(fileReader != null) {
				try {
					fileReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return shapes;
	}
	
	public static void main(String[] args) {
		CsvShapeParser parser = new CsvShapeParser();
		parser.loadShapes().stream()
			.forEach(c -> {
				System.out.println(c.getName() + "(" + c.getSize() + " " + c.getType() + ")");
				System.out.println("AC " + c.getArmorCount() + ", touch " + c.getTouchArmorCount() + ", flat " + c.getFlatArmorCount());
				c.getAttacks().stream().forEach(atk -> System.out.println(atk.getNumber() + " " + atk.getName() + "(" + atk.getDmgDie() + ")"));
				c.getSpecialAtks().stream().forEach(sa -> System.out.println(sa.getName() + ": " + sa.getDescription()));
			});

	}

}
