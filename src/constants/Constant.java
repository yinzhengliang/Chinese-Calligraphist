package constants;

import java.util.ArrayList;
import java.util.List;

public final class Constant {
	// System & Resource root path
	public static final String CURRENT_ROOT = System.getProperty("user.dir") + "/";
	public static final String RESOURCE_PATH = CURRENT_ROOT + "resources/";

	// Images
	public static final String IMAGE_PATH = RESOURCE_PATH + "Images/";

	// Button Icons
	public static final String BUTTON_ICON_PATH = IMAGE_PATH + "buttonIcons/";
	// UNDO
	public static final String BUTTON_UNDO_UNSELECT_ICON = "undogreen.png";
	public static final String BUTTON_UNDO_SELECTED_ICON = "undored.png";
	// REDO
	public static final String BUTTON_REDO_UNSELECT_ICON = "redogreen.png";
	public static final String BUTTON_REDO_SELECTED_ICON = "redored.png";
	// PREV
	public static final String BUTTON_PREV_UNSELECT_ICON = "prevblue.png";
	public static final String BUTTON_PREV_SELECTED_ICON = "prevorange.png";
	// NEXT
	public static final String BUTTON_NEXT_UNSELECT_ICON = "nextblue.png";
	public static final String BUTTON_NEXT_SELECTED_ICON = "nextorange.png";
	// CLEAR
	public static final String BUTTON_CLEAR_UNSELECT_ICON = "clear.png";
	public static final String BUTTON_CLEAR_SELECTED_ICON = "clearon.png";

	// Backgronds Images
	public static final String BACKGROUND_IMAGE_PATH = IMAGE_PATH + "backgroundImages/";
	// Writing Background
	public static final String PAPER_ICON = "paper.png";
	public static final String PAPER_SMALL_ICON = "smallpaper.png";

	// Pen Icons
	public static final String PEN_ICON_PATH = IMAGE_PATH + "penIcons/";
	// Writing Background
	public static final String PEN_ICON = "pencil.png";

	// ProblemCount Images
	public static final String PROBLEMCOUNT_IMGAGE_PATH = IMAGE_PATH + "problemCountImages/";
	public static final List<String> PROBLEMCOUNT_ICONS = new ArrayList<String>();

	public static final String PROBLEMSET_PATH = RESOURCE_PATH + "ProblemSet/";
	// public static final String

	public static final String TEMPLATE_PATH = "";

	// problem icon filename
	public static final String PROBLEM_CHARACTER_MID_ICON = "/shape_mid.png";
	public static final String PROBLEM_CHARACTER_SMALL_ICON = "/shape.png";
	public static final String PROBLEM_DESCRIPTION_FILE = "/description.txt";

	// template directory
	public static final String TEMPLATE_DIR = RESOURCE_PATH + "Templates/";

	// model directory
	public static final String MODEL_DIR = RESOURCE_PATH + "models/";

	// shape definition directory
	public static final String SHAPE_DEFINE_DIR = RESOURCE_PATH + "shapeDefinition/";
	public static final String RADICAL_CHARACTER_DEFINE_DIR = SHAPE_DEFINE_DIR + "Character/";
	public static final String STROKE_SHAPE_DEFINE_DIR = SHAPE_DEFINE_DIR + "Stroke/";
	public static final String TrainingData = RESOURCE_PATH + "MySolutionFeatureExtraction/myResultTT.arff";
//	public static final String TrainingData = RESOURCE_PATH + "MySolutionFeatureExtraction/model.arff";

	public Constant() {
		for (int i = 1; i <= 14; i++)
			PROBLEMCOUNT_ICONS.add(String.format("Problem%d.png", i));
	}
}
