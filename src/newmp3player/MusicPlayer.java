package newmp3player;

import java.io.File;
import javafx.animation.PauseTransition;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;

public class MusicPlayer {
    
    private MediaPlayer player;
    private Media musica;
    private int volume;
    public DoubleProperty barUpdater = new SimpleDoubleProperty(.0);

    private boolean playing = false;
    private boolean loopable;

    public Media getMusica() {
        return musica;
    }
    
    public void setMusica(Media musica) {
        
        this.musica = musica;
        
    }
    
    public MediaPlayer getPlayer() {
        return player;
    }

    public void setPlayer(MediaPlayer player) {
        this.player = player;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public boolean isPlaying() {
        return playing;
    }

    public void setPlaying(boolean playing) {
        this.playing = playing;
    }

    public boolean isLoopable() {
        return loopable;
    }

    public void setLoopable(boolean loopable) {
        this.loopable = loopable;
    }
    
    //Media urlSong = new Media(new File(musica.getURL()).toURI().toString());
    //MediaPlayer mediaPlayer = new MediaPlayer(urlSong);
    
    public Duration getDuracao() {
        
        return this.musica.getDuration();
        
    }
    
    public void trocarMusica() {
        
        if(this.isPlaying()){
         this.player.stop();   
        }
        
        MediaPlayer mediaPlayer = new MediaPlayer(this.getMusica());
        
        this.setPlayer(mediaPlayer);
        
  
        
        this.playMusica();
        
        this.playing=true;
        
        progressTrigger();
        
    }
    
    public void progressTrigger(){
        
        PauseTransition wait = new PauseTransition(Duration.seconds(0.05)); //talvez esteja muito low, não sei se trava ou algo do tipo
        wait.setOnFinished((e) -> {
            
            double progressPercentage = this.getPlayer().getCurrentTime().toSeconds() / this.getPlayer().getTotalDuration().toSeconds();
        
            barUpdater.set(progressPercentage);
            
            if(progressPercentage>=1){
                wait.stop();
                return;
            }
            
            wait.playFromStart();
            
        });
        
        wait.play();
        
    }
    
    public void pauseMusica() {
        
        if(this.isPlaying()){
            this.player.pause();
            this.setPlaying(false);
        }else{
            this.player.play();
            this.setPlaying(true);
        }
        
        
    }
    
    public void playMusica() {
        
        this.player.play();
        
    }

}
