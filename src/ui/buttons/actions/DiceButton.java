package ui.buttons.actions;

import java.util.Map;

import game.GameMaster;
import models.GameState;
import models.dice.DiceFace;
import ai.actions.IllegalActionException;
import ai.actions.SelectDieAction;
import ui.BBImage;
import ui.BloodBowlUI;
import ui.InputManager;
import ui.buttons.BBButton;

public class DiceButton extends BBButton {

	Map<DiceFace, BBImage> faceMap;
	int num;
	
	public DiceButton(int centerX, int centerY, int num, Map<DiceFace, BBImage> faceMap, BloodBowlUI ui, boolean active) {
		super(centerX, centerY, faceMap.get(null).getImage(), faceMap.get(null).getImage(), ui, active);
		this.faceMap = faceMap;
		this.num = num;
	}

	@Override
	public boolean clickedLayer(GameMaster master, BloodBowlUI ui,
			InputManager input) throws IllegalActionException {
		
		if(master.getState().getCurrentDiceRoll() == null && 
				master.getState().getCurrentDiceRoll().getFaces().size() - 1 >= num){
			
			master.act(new SelectDieAction(num));

		}
		
		return true;
	}

	@Override
	public void checkLayerActivation(GameState state, BloodBowlUI ui) {
		
		if (state.getCurrentDiceRoll() != null && state.getCurrentDiceRoll().getFaces().size() - 1 >= num){
			this.image = faceMap.get(state.getCurrentDiceRoll().getFaces().get(num)).getImage();
			this.imageHover = this.image;
		} else {
			this.image = faceMap.get(null).getImage();
			this.imageHover = this.image;
		}
	}

}
