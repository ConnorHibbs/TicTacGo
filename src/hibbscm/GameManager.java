package hibbscm;

import com.google.gson.Gson;

public class GameManager {

    public void sendPlay() {
        // settings knows the player number
        //

        Gson gson = new Gson();

        Play play = new Play(1, 4, 6);

        String myJson = gson.toJson(play, Play.class);

        System.out.println(myJson);





    }

    public void receivePlay() {
        // TODO
        // TODO advance the turn
    }
}
