/*
@ASSESSME.USERID: mm2945
@ASSESSME.AUTHOR: Martin Mudrinic
@ASSESSME.DESCRIPTION: 
@ASSESSME.ANALYZE: YES
*/

package network;

public final class Protocol {

    private Protocol() {

    }

    public static final String JOIN = "JOIN";
    public static final String MOVE = "MOVE";
    public static final String CHAT = "CHAT";

    public static final String STATE = "STATE";
    public static final String CHAT_BROADCAST = "CHAT_BROADCAST";
    public static final String PLAYER_JOINED = "PLAYER_JOINED";
    public static final String PLAYER_LEFT = "PLAYER_LEFT";
    public static final String GAME_OVER = "GAME_OVER";
}