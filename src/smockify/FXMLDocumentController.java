/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smockify;


import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import javafx.scene.media.Media;

/*
Necessários para iniciar uma música .mp3
*/

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javax.imageio.ImageIO;
import org.farng.mp3.TagException;

/**
 *
 * @author Santana
 */
public class FXMLDocumentController implements Initializable {
    
    private boolean testButton=false;
    
    MusicPlayer musicPlayer = new MusicPlayer();
    
    @FXML
    private Label musicaNome;
    
    @FXML
    private Label musicaTimer;
    
    @FXML
    private Circle songCircle;
    
    @FXML
    private ProgressBar progressBar;
    
    @FXML
    private ImageView playPause;
    
    @FXML
    private ImageView repeat;
    
    @FXML
    private Slider sliderVolume;
    
    @FXML
    private ImageView previousSong;
    
    @FXML
    private ImageView shuffler;
    
    @FXML
    private ImageView nextSong;
    
    @FXML
    private ListView<String> listView = new ListView<String>();
    
    ArrayList<AcessoRapido> arrayMusica = new ArrayList<AcessoRapido>();

    public boolean isTestButton() {
        return testButton;
    }

    public void setTestButton(boolean testButton) {
        this.testButton = testButton;
    }

    public MusicPlayer getMusicPlayer() {
        return musicPlayer;
    }

    public void setMusicPlayer(MusicPlayer musicPlayer) {
        this.musicPlayer = musicPlayer;
    }

    public Label getMusicaNome() {
        return musicaNome;
    }

    public void setMusicaNome(Label musicaNome) {
        this.musicaNome = musicaNome;
    }

    public Circle getSongCircle() {
        return songCircle;
    }

    public void setSongCircle(Circle songCircle) {
        this.songCircle = songCircle;
    }

    public ProgressBar getProgressBar() {
        return progressBar;
    }

    public void setProgressBar(ProgressBar progressBar) {
        this.progressBar = progressBar;
    }

    public ImageView getPlayPause() {
        return playPause;
    }

    public void setPlayPause(ImageView playPause) {
        this.playPause = playPause;
    }

    public ImageView getRepeat() {
        return repeat;
    }

    public void setRepeat(ImageView repeat) {
        this.repeat = repeat;
    }
    
    public ImageView setNextSong() {
        return nextSong;
    }

    public void setNextSong(ImageView nextSong) {
        this.nextSong = nextSong;
    }
    
    public ImageView getPreviousSong() {
        return previousSong;
    }

    public void setPreviousSong(ImageView previousSong) {
        this.previousSong = previousSong;
    }

    public Slider getSliderVolume() {
        return sliderVolume;
    }

    public void setSliderVolume(Slider sliderVolume) {
        this.sliderVolume = sliderVolume;
    }

    public ListView<String> getListView() {
        return listView;
    }

    public void setListView(ListView<String> listView) {
        this.listView = listView;
    }

    public ArrayList<AcessoRapido> getArrayMusica() {
        return arrayMusica;
    }

    public void setArrayMusica(ArrayList<AcessoRapido> arrayMusica) {
        this.arrayMusica = arrayMusica;
    }
    
    public Label getMusicaTimer(){
        return this.musicaTimer;
    }
    
    public boolean verifyContains(File URL){
        
        for(int x=0; x<arrayMusica.size(); x++){
            if(arrayMusica.get(x).getUrl().equals(URL)){
                return true;
            }
        }
        
        return false;
    }
    
    public void setMedia2Screen(File file) throws IOException, UnsupportedTagException, InvalidDataException{
        
            Mp3File mp3file = new Mp3File(file);
            
            //replaceAll("\uFFFD", "") serve para remover os caracteres esquisitos que aparecem, que tem alguma relação com UCS-2 e não conseguir interpretar o texto como UTF-8.
                
            musicPlayer.setMusica(new Media(file.toURI().toString()));
            
            musicPlayer.trocarMusica();
            
            playPause.setImage(new Image(new File("src/resources/pause.png").toURI().toString()));
    
            if(!this.verifyContains(file)) {
                
                arrayMusica.add(new AcessoRapido(file, file.getName()));

                ArrayList<String> acessoTitles = new ArrayList<String>();

                for(int x=0; x<arrayMusica.size(); x++){
                    acessoTitles.add(arrayMusica.get(x).getTitle());
                }

                String[] recentSongs = acessoTitles.toArray(new String[0]);

                this.setSongList(recentSongs);
                
            }
            
            if(mp3file.getId3v2Tag().getTitle() == null){
                
                musicaNome.setText(file.getName());
                
                this.setSongAvatar(new Image(new File("src/smockify/avatar.png").toURI().toString()));
                
            }else{
                
                musicaNome.setText(mp3file.getId3v2Tag().getTitle());
                
                if(mp3file.getId3v2Tag().getAlbumImage() == null){
                    this.setSongAvatar(new Image(new File("src/smockify/avatar.png").toURI().toString()));
                    return;
                }
                    
                BufferedImage img = ImageIO.read(new ByteArrayInputStream(mp3file.getId3v2Tag().getAlbumImage()));;
                
                Image capturedImg = SwingFXUtils.toFXImage(img, null);
                
                this.setSongAvatar(capturedImg);
                
            }
        
    }
    
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
    private void dropSong(DragEvent event) throws TagException, IOException, InvalidDataException, UnsupportedTagException {
        
        Dragboard db = event.getDragboard();
        
        System.out.println(db.getFiles().toArray().length);
        
        if(db.hasFiles()) {
            
            File file = db.getFiles().iterator().next();
            
            this.setMedia2Screen(file);
            
        }
        
        event.setDropCompleted(true);
        event.consume();
        
    }
    
    @FXML
    public void setSongAvatar(Image img) {
        
        if(img.isError()){
            this.setSongAvatar(new Image(new File("src/smockify/avatar.png").toURI().toString()));
            return;
        }
        
        songCircle.setFill(new ImagePattern(img));
        
    }
    
    public void getSongURL(String title) throws IOException, UnsupportedTagException, InvalidDataException{
        
        for(int x=0; x<arrayMusica.size(); x++){
            
            if(arrayMusica.get(x).getTitle().equals(title)){
                
                setMedia2Screen(arrayMusica.get(x).getUrl());
                
                playPause.setImage(new Image(new File("src/resources/pause.png").toURI().toString()));
            }
        }
    };
    
    public void setSongList(String songArray[]){
        
        ObservableList<String> items = FXCollections.observableArrayList ();
        
        items = FXCollections.observableArrayList (songArray);
        
        this.listView.setItems(items);
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        this.getMusicPlayer().setControlador(this);
        
        this.getProgressBar().progressProperty().bind(this.getMusicPlayer().getBarUpdater());

        this.setSongAvatar(new Image(new File("src/smockify/avatar.png").toURI().toString()));
        
        //String checking[] = {"a"};
        
        //this.setSongList(checking);
        
        sliderVolume.setOnMouseReleased(e -> {
            
            if(musicPlayer.getPlayer() == null){
                return;
            }
            
            musicPlayer.setVolume((sliderVolume.getValue()/100));
            
            System.out.println(musicPlayer.getPlayer().getVolume());
            
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
        
        playPause.setOnMouseClicked((MouseEvent e) -> {
            
            if(musicPlayer.getPlayer() == null){
                return;
            }
            
            if(musicPlayer.getPlayer().getCurrentTime().toSeconds() / musicPlayer.getPlayer().getTotalDuration().toSeconds() == 1){
                musicPlayer.trocarMusica();
                musicPlayer.pauseMusica();
            }
            
            if(musicPlayer.isPlaying()) {
                playPause.setImage(new Image(new File("src/resources/play.png").toURI().toString()));
                musicPlayer.pauseMusica();
            }else{
                playPause.setImage(new Image(new File("src/resources/pause.png").toURI().toString()));
                musicPlayer.pauseMusica();
            }
            
        });
        
        listView.setOnMouseClicked(new EventHandler<MouseEvent>() {
           @Override
           public void handle(MouseEvent event) {
               
               try {
                   
                   if(listView.getSelectionModel().getSelectedItem() == null){
                       return;
                   }
                   
                   getSongURL(listView.getSelectionModel().getSelectedItem());
                   
               } catch (IOException ex) {
                   Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
               } catch (UnsupportedTagException ex) {
                   Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
               } catch (InvalidDataException ex) {
                   Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
               }
               
           }
        });
        
        nextSong.setOnMouseClicked((MouseEvent e) -> {
            
          int arrayLength = getArrayMusica().toArray().length - 1;
           
           if(getArrayMusica().toArray().length <= 1 || getArrayMusica().get(arrayLength).getUrl().toURI().toString().equals(getMusicPlayer().getMusica().getSource())){
               return;
           }
           
           for(int x = 0; x<=arrayLength; x++){
               
               if(getMusicPlayer().getMusica().getSource().equals(getArrayMusica().get(x).getUrl().toURI().toString())){
                   
                   try {
                       setMedia2Screen(getArrayMusica().get(x+1).getUrl());
                   } catch (IOException ex) {
                       Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                   } catch (UnsupportedTagException ex) {
                       Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                   } catch (InvalidDataException ex) {
                       Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                   }
                   
                   return;
                   
               }
               
               
           }
           
        });
        
        previousSong.setOnMouseClicked((MouseEvent e) -> {
           
           
           if(getArrayMusica().toArray().length <= 1 || getArrayMusica().get(0).getUrl().toURI().toString().equals(getMusicPlayer().getMusica().getSource())){
               return;
           } 
           
           for(int x = 0; x<getArrayMusica().size(); x++){
               
               if(getArrayMusica().get(x).getUrl().toURI().toString().equals(getMusicPlayer().getMusica().getSource())){
                   
                   try {
                       setMedia2Screen(getArrayMusica().get(x-1).getUrl());
                   } catch (IOException ex) {
                       Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                   } catch (UnsupportedTagException ex) {
                       Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                   } catch (InvalidDataException ex) {
                       Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                   }
                   
                   return;
                   
               }
               
               
           }
 
        });
        
        getShuffler().setOnMouseClicked((MouseEvent e) -> {
           
            if(getMusicPlayer().getPlayer() == null){
                return;
            }
            
            boolean decision = getMusicPlayer().isShufflable() ? false : true;
            
            getMusicPlayer().setShufflable( decision );
            
            if(getMusicPlayer().isShufflable()){
                getShuffler().setImage(new Image(new File("src/resources/shuffle2.png").toURI().toString()));
            }else{
                getShuffler().setImage(new Image(new File("src/resources/shuffle.png").toURI().toString()));
            }
            
        });
        
        getRepeat().setOnMouseClicked((MouseEvent e) -> {
            
            if(getMusicPlayer().getPlayer() == null){
                return;
            }
            
            getMusicPlayer().mudarRepeat(); //operador ternario aqui seria uma melhor opção
            
            if(getMusicPlayer().isLoopable()){
                
                getRepeat().setImage(new Image(new File("src/resources/repeat2.png").toURI().toString()));
                
            }else{
                
                getRepeat().setImage(new Image(new File("src/resources/repeat.png").toURI().toString()));
                
            }
            
            
            
        });
        
        
    }    

    public ImageView getShuffler() {
        return getShuffler();
    }

    public void setShuffler(ImageView shuffler) {
        this.shuffler = shuffler;
    }
    
}
