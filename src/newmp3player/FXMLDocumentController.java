/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newmp3player;


import java.io.File;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/*
Necessários para iniciar uma música .mp3
*/

import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.PauseTransition;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Background;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

/**
 *
 * @author Santana
 */
public class FXMLDocumentController implements Initializable {
    
    private boolean testButton=false;
    
    //Media hit = new Media(new File("src/newmp3player/song.mp3").toURI().toString());
    //MediaPlayer mediaPlayer = new MediaPlayer(hit);
    
    MusicPlayer musicPlayer = new MusicPlayer();
    
    @FXML
    private Label label;
    
    @FXML
    private Circle songCircle;
    
    @FXML
    public ProgressBar progressBar;
    
    @FXML
    public ImageView playPause;
    
    @FXML
    public ImageView repeat;
    
    
    //Você vai provavelmente querer remover isso aqui.
    /*public void progressTrigger(){
        
        PauseTransition wait = new PauseTransition(Duration.seconds(0.1));
        wait.setOnFinished((e) -> {
            
            System.out.println("Loop acontecendo.");
            
            double progressPercentage = musicPlayer.getPlayer().getCurrentTime().toSeconds() / musicPlayer.getPlayer().getTotalDuration().toSeconds();
        
            progressBar.setProgress(progressPercentage);
            
            if(progressPercentage>=1){
                System.out.println("Loop encerrado.");
                wait.stop();
                return;
            }
            
            wait.playFromStart();
            
        });
        
        wait.play();
        
    }
    */
    
    
    
    @FXML
    private void dragDetectSong(MouseEvent event) {
        
        System.out.println("Drag detected...");
        
    }
    
    @FXML
    private void dragSong(DragEvent event) {
        
        event.acceptTransferModes(TransferMode.ANY);
        
        event.consume();
        
    }
    
    @FXML
    private void dropSong(DragEvent event) {
        
        Dragboard db = event.getDragboard();
        
        System.out.println(db.getFiles().toArray().length);
        
        if(db.hasFiles()) {
            
            File file = db.getFiles().iterator().next();
            
            musicPlayer.setMusica(new Media(file.toURI().toString()));
            
            musicPlayer.trocarMusica();
            
            playPause.setImage(new Image(new File("src/resources/pause.png").toURI().toString()));
            
        }
        
        event.setDropCompleted(true);
        event.consume();
        
    }
    
    @FXML
    public void setSongAvatar(Image img) {
        
        songCircle.setFill(new ImagePattern(img));
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        progressBar.progressProperty().bind(musicPlayer.barUpdater);

        this.setSongAvatar(new Image(new File("src/newmp3player/avatar.png").toURI().toString()));
        
        playPause.setOnMouseClicked((MouseEvent e) -> {
            
            if(musicPlayer.getPlayer() == null){
                return;
            }
            
            if(musicPlayer.isPlaying()) {
                playPause.setImage(new Image(new File("src/resources/play.png").toURI().toString()));
                musicPlayer.pauseMusica();
            }else{
                playPause.setImage(new Image(new File("src/resources/pause.png").toURI().toString()));
                musicPlayer.pauseMusica();
            }
            
            
            
        });
        
        playPause.setOnMouseClicked((MouseEvent e) -> {
            
            if(musicPlayer.getPlayer() == null){
                return;
            }
            
            if(musicPlayer.isPlaying()) {
                playPause.setImage(new Image(new File("src/resources/play.png").toURI().toString()));
                musicPlayer.pauseMusica();
            }else{
                playPause.setImage(new Image(new File("src/resources/pause.png").toURI().toString()));
                musicPlayer.pauseMusica();
            }
            
        });
        
        repeat.setOnMouseClicked((MouseEvent e) -> {
            
            musicPlayer.mudarRepeat();
            
            if(musicPlayer.isLoopable()){
                
                repeat.setImage(new Image(new File("src/resources/repeat2.png").toURI().toString()));
                
            }else{
                
                repeat.setImage(new Image(new File("src/resources/repeat.png").toURI().toString()));
                
            }
            
            
            
        });
        
        
    }    
    
}
