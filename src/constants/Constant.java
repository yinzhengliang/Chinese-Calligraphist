package constants;

import java.util.ArrayList;
import java.util.List;

public final class Constant {
	// System & Resource root path
	public final static String CURRENT_ROOT = System.getProperty("user.dir") + "/";
	public final static String RESOURCE_PATH = CURRENT_ROOT + "resources/";

	// Images
	public final static String IMAGE_PATH = RESOURCE_PATH + "Images/";

	// Button Icons
	public final static String BUTTON_ICON_PATH = IMAGE_PATH + "buttonIcons/";
	// UNDO
	public final static String BUTTON_UNDO_UNSELECT_ICON = "undogreen.png";
	public final static String BUTTON_UNDO_SELECTED_ICON = "undored.png";
	// REDO
	public final static String BUTTON_REDO_UNSELECT_ICON = "redogreen.png";
	public final static String BUTTON_REDO_SELECTED_ICON = "redored.png";
	// PREV
	public final static String BUTTON_PREV_UNSELECT_ICON = "prevblue.png";
	public final static String BUTTON_PREV_SELECTED_ICON = "prevorange.png";
	// NEXT
	public final static String BUTTON_NEXT_UNSELECT_ICON = "nextblue.png";
	public final static String BUTTON_NEXT_SELECTED_ICON = "nextorange.png";
	// CLEAR
	public final static String BUTTON_CLEAR_UNSELECT_ICON = "clear.png";
	public final static String BUTTON_CLEAR_SELECTED_ICON = "clearon.png";

	// Backgronds Images
	public final static String BACKGROUND_IMAGE_PATH = IMAGE_PATH + "backgroundImages/";
	// Writing Background
	public final static String PAPER_ICON = "paper.png";
	public final static String PAPER_SMALL_ICON = "smallpaper.png";

	// Pen Icons
	public final static String PEN_ICON_PATH = IMAGE_PATH + "penIcons/";
	// Writing Background
	public final static String PEN_ICON = "pencil.png";
	
	// ProblemCount Images
	public final static String PROBLEMCOUNT_IMGAGE_PATH = IMAGE_PATH + "problemCountImages/"; 
	public final static List<String> PROBLEMCOUNT_ICONS = new ArrayList<String>();

	public final static String PROBLEMSET_PATH = RESOURCE_PATH + "ProblemSet/";
	// public final static String

	public final static String TEMPLATE_PATH = "";

	// problem icon filename
	public final static String PROBLEM_CHARACTER_MID_ICON = "/shape_mid.png";
	public final static String PROBLEM_CHARACTER_SMALL_ICON = "/shape.png";
	public final static String PROBLEM_DESCRIPTION_FILE = "/description.txt";
	
	// template directory
	public final static String TEMPLATE_DIR = RESOURCE_PATH + "Templates/";
	
	
	// model directory
	public final static String MODEL_DIR = RESOURCE_PATH + "models/";
	
	
	public Constant() {
		for (int i = 1; i <= 14; i++)
			PROBLEMCOUNT_ICONS.add(String.format("Problem%d.png", i));
	}
}
