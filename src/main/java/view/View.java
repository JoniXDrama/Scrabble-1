package view;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Label;
import model.Model;
import model.network.GameServer;
import view_model.ViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class View implements Observer {
    LandingPage landingPage;
    GamePage gamePage = GamePage.getGP();
    ViewModel vm = ViewModel.getViewModel();


    public View() {
        this.landingPage  = new LandingPage();
        GamePage.getGP();
    }

    public void setViewModel() {
        vm.playerQuery.bind(gamePage.playerTmpQuery.textProperty());
        gamePage.scoreLabel.textProperty().bind(vm.score.asString());
        //binding for the playerHand
        setPlayerHand();


    }

    public void setPlayerHand(){
        int ind = 0;
        List<Label> rackLabels = new ArrayList<>(); // tmprackLabels for the method createRack
        for (String strTile : vm.playerHand) {
            Label label = new Label(strTile);
            String tmpStr = vm.playerHand.get(ind);
            StringProperty strProperty = new SimpleStringProperty(tmpStr); // converting String to StringProperty
            label.textProperty().bindBidirectional(strProperty); // binding between label to StringProperty
            rackLabels.add(label);
            ind++;
        }
        Platform.runLater(()->{
            gamePage.createRack(rackLabels);
        });
    }

    @Override
    public void update(Observable o, Object arg) {

    }

    //GETTES
    public LandingPage getLandingPage() {return landingPage;}
    public GamePage getGamePage() {return gamePage;}
    private  static class ViewHolder{ public static final View v = new View();}
    public static View getView() {return ViewHolder.v;}



}