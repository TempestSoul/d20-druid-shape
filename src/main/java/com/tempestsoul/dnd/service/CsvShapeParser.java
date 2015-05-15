package com.tempestsoul.dnd.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import com.tempestsoul.dnd.d20.Creature;
import com.tempestsoul.dnd.service.parser.ShapeListParser;

public class CsvShapeParser implements ShapeLoadService {

	@Override
	public List<Creature> loadShapes() {
		List<Creature> shapes = null;
		
		File shapeFile = new File("src/main/resources/druid shapes.csv");
		FileReader fileReader = null;
		try {
			fileReader = new FileReader(shapeFile);
			ShapeListParser parser = new ShapeListParser();
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
	
	

}
