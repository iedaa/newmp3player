
package smockify;

import java.io.File;

public class AcessoRapido {
    
    private File url;
    private String title;
    private String playlist;

    public AcessoRapido(File url, String title) {
        this.url = url;
        this.title = title;
        this.playlist = playlist;
    }

    public File getUrl() {
        return url;
    }

    public void setUrl(File url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPlaylist() {
        return playlist;
    }

    public void setPlaylist(String playlist) {
        this.playlist = playlist;
    }
    
    
    
}
