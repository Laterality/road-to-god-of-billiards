package com.primitive.road_to_god_of_billiard.utility;

/**
 * Created by 신진우- on 2015-10-14.
 * Information for Server IO
 */
public class ServerInfo
{
	public static final String BASE_URL = "http://210.125.219.56";

	public static final String API_USER_GET = "/4Billion/api/Players/{userId}";
	public static final String API_GAME_GET = "/4Billion/api/Game/{gameId}";
	public static final String API_GAMING_GET = "/4Billion/api/Gamming/{gamingId}";
	public static final String API_RESULT_GET = "/4Billion/api/GameResult/{resultId}";
	public static final String API_POST_GET = "/4Billion/api/Board/{postId}";
	public static final String API_LIKE_GET = "/4Billion/api/Like/{postId}";
	public static final String API_MY_POSTS_GET = "/4Billion/api/MyWriting/{userId}";
	public static final String API_MY_REPLIES_GET = "/4Billion/api/MyComment/{userId}";
	public static final String API_REPLIES_BY_POST_GET = "/4Billion/api/Comment/{postId}";
	public static final String API_POST_ORDERBY_POP_GET = "/4Billion/api/Popularity";
	public static final String API_POST_ORDERBY_LAT_GET = "/4Billion/api/Newest"; /* Use API_POST_ORDERBY_LAT by POST*/
	public static final String API_RECORD_GET = "/4Billion/api/Record/{userId}";
	public static final String API_TEAM_GET = "/4Billion/api/Team/{TeamCode}";
	public static final String API_TEAM_MEMBERS_GET = "/4Billion/api/CreateTeam/{TeamCode}";
	public static final String API_TEAM_GAME_GET = "/4Billion/api/TeamGame/{teamGameId}";
	public static final String API_TEAM_GAMING_GET = "/4Billion/api/TeamGamming/{teamGamingId}";
	public static final String API_TEAM_GAME_RESULT_GET = "/4Billion/api/TeamGameResult/{teamGameId}";
	public static final String API_TEAM_BOARD_GET_GET = "/4Billion/api/TeamBoard/{postId}";
	public static final String API_FRIEND_LIST_GET = "/4Billion/api/Friend/{userId}";





	public static final String API_RECEIVED_GAME_GET = "/4Billion/api/ReceiveId/{receiverId}";
	public static final String API_SENT_GAME_GET = "4Billion/api/StatusFinder/{senderId}";

	public static final String API_USER_PATH = "userId";
	public static final String API_GAME_PATH = "gameId";
	public static final String API_GAMING_PATH = "gamingId";
	public static final String API_RECEIVED_GAME_PATH = "receiverId";
	public static final String API_SENT_GAME_PATH = "senderId";
	public static final String API_SENDER_SCORE_UPDATE_PATH = "senderId";
	public static final String API_RECEIVER_SCORE_UPDATE_PATH = "receiverId";
	public static final String API_RESULT_PATH = "resultId";
	public static final String API_POST_PATH = "postId";
	public static final String API_LIKE_PATH = "postId";
	public static final String API_MY_POSTS_PATH = "userId";
	public static final String API_MY_REPLIES_PATH = "userId";
	public static final String API_REPLY_PUT_PATH = "replyId";
	public static final String API_REPLIES_BY_POST_PATH = "postId";
	public static final String API_RECORD_PATH = "userId";
	public static final String API_TEAM_SIGNIN_PATH = "userId";
	public static final String API_TEAM_MEMBERS_PATH = "TeamCode";
	public static final String API_TEAM_GAME_PATH = "teamGameId";
	public static final String API_TEAM_GAME_RESULT_PATH = "teamGameId";
	public static final String API_TEAM_GAMING_SENDER_PATH = "{sendTeam}";
	public static final String API_TEAM_GAMING_RECEIVER_PATH = "{receiveTeam}";
	public static final String API_TEAM_BOARD_PATH = "postId";
	public static final String API_TEAM_PATH = "TeamCode";
	public static final String API_FRIEND_LIST_PATH = "userId";



	public static final String API_USER_POST = "/4Billion/api/Players/";
	public static final String API_GAME_POST = "/4Billion/api/Game/";
	public static final String API_GAMING_POST = "/4Billion/api/Gamming/";
	public static final String API_RESULT_POST = "/4Billion/api/GameResult";
	public static final String API_POST_POST = "/4Billion/api/Board/";
	public static final String API_REPLY_POST = "/4Billion/api/Comment/";
	public static final String API_POST_ORDERBY_LAT_POST = "/4Billion/api/Newest";
	public static final String API_POST_ORDERBY_POP_POST = "/4Billion/api/Popularity";
	public static final String API_SEARCH_USER_POST = "/4Billion/api/NickSearch/";
	public static final String API_TEAM_ADD_POST = "/4Billion/api/CreateTeam";
	public static final String API_TEAM_GAME_POST = "/4Billion/api/TeamGame";
	public static final String API_TEAM_GAME_RESULT_POST = "/4Billion/api/TeamGameResult";
	public static final String API_TEAM_TEAM_BOARD_POST = "/4Billion/api/TeamBoard";
	public static final String API_TEAM_NAME_REITERATION_POST = "/4Billion/api/Team";
	public static final String API_REITERATION_CHECK_EMAIL_POST = "/4Billion/api/E_MailOverLap";
	public static final String API_REITERATION_CHECK_USERNAME_POST = "/4Billion/api/NicknameOverLap";
	public static final String API_SEARCH_GAME_PVP = "/4Billion/api/FindPlayer";
	public static final String API_SEARCH_GAME_TVT = "/4Billion/api/FindTeam";
	public static final String API_LOGIN_POST = "/4Billion/api/Login";


	public static final String API_USER_PUT = "/4Billion/api/Players/{userId}";
	public static final String API_GAME_PUT = "/4Billion/api/Game/{gameId}";
	public static final String API_GAMING_SENDER_PUT = "/4Billion/api/Gamming/SendUserScore/{senderId}";
	public static final String API_GAMING_RECEIVER_PUT = "4Billion/api//Gamming2/ReceiveUserScore/{receiverId}";
	public static final String API_RESULT_PUT = "/4Billion/api/GameResult/{resultId}"; // deprecated, don't use
	public static final String API_POST_PUT = "/4Billion/api/board/{postId}";
	public static final String API_REPLY_PUT = "/4Billion/api/Comment/{replyId}";
	public static final String API_TEAM_SIGNIN_PUT = "/4Billion/api/CreateTeam/{userId}";
	public static final String API_TEAM_GAME_PUT = "/4Billion/api/Team/Game/{teamGameId}";
	public static final String API_TEAM_GAMING_SENDER_PUT = "/4Billion/api/SendTeamScore/{sendTeam}";
	public static final String API_TEAM_GAMING_RECEIVER_PUT = "/4Billion/api/receiveTeamScore/{receiveTeam}";
	public static final String API_TEAM_GAME_RESULT_PUT = "/4Billion/api/TeamGameResult/{teamGameResult}";

	public static final String API_POST_DELETE = "/4Billion/api/Board/{postId}";
	public static final String API_TEAM_DELETE = "/4Billion/api/Team/{TeamCode}";

}
