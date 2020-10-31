package com.ely.secretify.api;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
//import org.brunocvcunha.instagram4j.Instagram4j;
//import org.brunocvcunha.instagram4j.requests.payload.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/")
public class GirisController {

    SerialPort comPort;
    String buffer = "";

    public GirisController() {

        comPort = SerialPort.getCommPorts()[0];
        comPort.openPort();




        comPort.addDataListener(new SerialPortDataListener() {
            @Override
            public int getListeningEvents() { return SerialPort.LISTENING_EVENT_DATA_AVAILABLE; }
            @Override
            public void serialEvent(SerialPortEvent event)
            {
                if (event.getEventType() != SerialPort.LISTENING_EVENT_DATA_AVAILABLE)
                    return;
                byte[] newData = new byte[comPort.bytesAvailable()];
                int numRead = comPort.readBytes(newData, newData.length);
                String str = new String(newData);

                buffer += str;
                if(buffer.endsWith("\n"))
                {
                    System.out.println("oyle bir mesac geldi kiiiiiii " + buffer );
                    buffer = "";
                }
            }
        });

    }



    private static Logger log = LoggerFactory.getLogger(GirisController.class);
    private List followersUsers;
    private List followingUsers;
//    private Instagram4j instagram4j;
//    private InstagramLoginResult instagramLoginResult;
//    private List<UserImageEntity> userList;

//    @Autowired
//    UserImageRepository userImageRepository;

//    private UserImageEntity userImageEntity;



//    private void deneme(Instagram4j instagram4j, InstagramLoginResult instagramLoginResult) throws IOException {
//        InstagramGetUserFollowersResult followersResult = new InstagramGetUserFollowersResult();
//        InstagramGetUserFollowersResult followingRequest;
//        InstagramUser instagramUser = new InstagramUser();
//        String username;
//        long pk;
//
//        InstagramSearchUsernameResult followingResult;
//        InstagramSearchUsernameResult userResult = instagram4j.sendRequest(new InstagramSearchUsernameRequest(instagram4j.getUsername()));
//        followersResult = instagram4j.sendRequest(new InstagramGetUserFollowersRequest(userResult.getUser().getPk()));
//        followingRequest = instagram4j.sendRequest(new InstagramGetUserFollowingRequest(userResult.getUser().getPk()));
//        followingUsers = followingRequest.getUsers();
//        followersUsers = followersResult.getUsers();
//        log.info("followerList: {}", followersResult);
//        log.info("followingList: {}", followingUsers.get(0));
//        username = followersResult.getUsers().get(1).username;
//        instagramUser.setUsername(username);
//
//        for (int i=0; i<followingUsers.size(); i++) {
//        //for (Object user : followingUsers){
//            pk = followingRequest.getUsers().get(i).getPk();
//            InstagramFeedResult instagramFeedResult = instagram4j.sendRequest(new InstagramUserFeedRequest(pk));
//            userImageEntity = new UserImageEntity();
//
//            for (InstagramFeedItem item : instagramFeedResult.getItems()) {
//                try {
//                    log.info("item.gettah: {}", item.getImage_versions2().getCandidates());
//                    userImageEntity.setUsername(item.getUser().username);
//                    userImageEntity.setImageFormat("JPEG");
//                    for (int j = 0; j < item.getImage_versions2().candidates.size(); j++) {
//                        userImageEntity.setImageURL(item.getImage_versions2().candidates.get(j).url);
//                        log.info(":::{}, {}, {}, {}, {}", i, j, userImageEntity.getUsername().length(), userImageEntity.getImageURL().length(), userImageEntity.getImageFormat().length());
//                        userImageRepository.save(userImageEntity);
//                    }
//                } catch (NullPointerException e) {
//                }
//            }
//        }
//    }


//    @RequestMapping(value = "photos/{username}", method = RequestMethod.GET)
//    public ResponseEntity<List<UserImageEntity>> getPhotos(@PathVariable String username){
//        log.info("usernamee: {}", username);
//        userList = (List<UserImageEntity>) userImageRepository.findAll();
//        return new ResponseEntity<>(userList, HttpStatus.OK);
//    }


    public void givenAmount_whenConversion_thenNotNull() throws IOException {
        URL url = new URL("https://api.currencyfreaks.com/latest?apikey=d9b286cad7984661ab465673edd9683b");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        JsonObject jsonObject = new JsonParser().parse(String.valueOf(content)).getAsJsonObject();
        JsonElement rates = jsonObject.get("rates");
        JsonObject jsonRates = rates.getAsJsonObject();
        JsonElement jj = jsonRates.get("TRY");

        log.info("json: {}", jj);



        in.close();
        con.disconnect();
    }

    @RequestMapping(value = "buton", method = RequestMethod.GET,  consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> butonaBas(){
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @RequestMapping(value = "basic", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> basicGetRequest(){
        log.info("---basic---");

        String str = new Date().toString()  + "\n";

        comPort.writeBytes(str.getBytes() , str.length());
        return new ResponseEntity<>("name", HttpStatus.ACCEPTED);
    }


    @RequestMapping(value = "logins", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> addSnippet(@RequestBody LoginWrapper data) throws IOException {
        log.info("geldi");
        givenAmount_whenConversion_thenNotNull();
//        Instagram4j instagram = Instagram4j.builder()
//                .username(data.getUsername())
//                .password(data.getPassword())
//                .build();
//        instagram.setup();
//
//        // Check login response
//        this.instagramLoginResult = instagram.login();
//
//        try {
//            String result = checkInstagramLoginResult(instagram, instagramLoginResult, true);
//            if (result.compareTo("success") == 0){
//                log.info("state: {}", result);
//                return new ResponseEntity<>(HttpStatus.OK);
//            }
//            else{
//                log.info("errör: {}", result);
//                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//    }

//    /**
//     * Check login response.
//     *
//     * @param instagram4j
//     * @param instagramLoginResult
//     * @param doReAuthentication
//     * @throws Exception
//     */
//    public String checkInstagramLoginResult(Instagram4j instagram4j, InstagramLoginResult instagramLoginResult,
//                                                 boolean doReAuthentication) throws Exception {
//        if (Objects.equals(instagramLoginResult.getStatus(), "ok")
//                && instagramLoginResult.getLogged_in_user() != null) {
//            // Login success
//            System.out.println("■Login success.");
//            this.instagram4j = instagram4j;
//            this.instagramLoginResult = instagramLoginResult;
//            //deneme(instagram4j, instagramLoginResult);
//            return "success";
//        } else if (Objects.equals(instagramLoginResult.getStatus(), "ok")) {
//            // Logged in user not exists
//            System.out.println("■Logged in user not exists.");
//
//            // TODO
//        } else if (Objects.equals(instagramLoginResult.getStatus(), "fail")) {
//            // Login failed
//
//            // Check error type
//            if (Objects.equals(instagramLoginResult.getError_type(), "checkpoint_challenge_required")) {
//                System.out.println("■Challenge URL : " + instagramLoginResult.getChallenge().getUrl());
//
//                // If do re-authentication
//                if (doReAuthentication) {
//                    // Get challenge URL
//                    String challengeUrl = instagramLoginResult.getChallenge().getApi_path().substring(1);
//
//                    // Reset
//                    String resetUrl = challengeUrl.replace("challenge", "challenge/reset");
//                    InstagramGetChallengeResult getChallengeResult = instagram4j
//                            .sendRequest(new InstagramResetChallengeRequest(resetUrl));
//                    System.out.println("■Reset result : " + getChallengeResult);
//
//                    // if "close"
//                    if (Objects.equals(getChallengeResult.getAction(), "close")) {
//                        // Get challenge response
//                        getChallengeResult = instagram4j
//                                .sendRequest(new InstagramGetChallengeRequest(challengeUrl));
//                        System.out.println("■Challenge response : " + getChallengeResult);
//                    }
//
//                    // Check step name
//                    if (Objects.equals(getChallengeResult.getStep_name(), "select_verify_method")) {
//                        // Select verify method
//
//                        // Get select verify method result
//                        InstagramSelectVerifyMethodResult postChallengeResult = instagram4j
//                                .sendRequest(new InstagramSelectVerifyMethodRequest(challengeUrl,
//                                        getChallengeResult.getStep_data().getChoice()));
//
//                        // If "close"
//                        if (Objects.equals(postChallengeResult.getAction(), "close")) {
//                            // Challenge was closed
//                            System.out.println("■Challenge was closed : " + postChallengeResult);
//
//                            // End
//                            return "fail";
//                        }
//
//                        // Security code has been sent
//                        System.out.println("■Security code has been sent : " + postChallengeResult);
//
//                        // Please input Security code
//                        System.out.println("Please input Security code");
//                        String securityCode = null;
//                        try (Scanner scanner = new Scanner(System.in)) {
//                                securityCode = scanner.nextLine();
//                        }
//
//                        // Send security code
//                        InstagramLoginResult securityCodeInstagramLoginResult = instagram4j
//                                .sendRequest(new InstagramSendSecurityCodeRequest(challengeUrl, securityCode));
//
//                        // Check login response
//                        checkInstagramLoginResult(instagram4j, securityCodeInstagramLoginResult, false);
//                    } else if (Objects.equals(getChallengeResult.getStep_name(), "verify_email")) {
//                        // Security code has been sent to E-mail
//                        System.out.println("■Security code has been sent to E-mail");
//
//                        // TODO
//                    } else if (Objects.equals(getChallengeResult.getStep_name(), "verify_code")) {
//                        // Security code has been sent to phone
//                        System.out.println("■Security code has been sent to phone");
//
//                        // TODO
//                    } else if (Objects.equals(getChallengeResult.getStep_name(), "submit_phone")) {
//                        // Unknown
//                        System.out.println("■Unknown.");
//
//                        // TODO
//                    } else if (Objects.equals(getChallengeResult.getStep_name(), "delta_login_review")) {
//                        // Maybe showing security confirmation view?
//                        System.out.println("■Maybe showing security confirmation view?");
//
//                        // FIXME Send request with choice
//                        InstagramSelectVerifyMethodResult instagramSelectVerifyMethodResult = instagram4j
//                                .sendRequest(new InstagramSelectVerifyMethodRequest(challengeUrl,
//                                        getChallengeResult.getStep_data().getChoice()));
//                        System.out.println(instagramSelectVerifyMethodResult);
//
//                        // TODO
//                    } else if (Objects.equals(getChallengeResult.getStep_name(), "change_password")) {
//                        // Change password needed
//                        System.out.println("■Change password needed.");
//                    } else if (Objects.equals(getChallengeResult.getAction(), "close")) {
//                        // Maybe already challenge closed at other device
//                        System.out.println("■Maybe already challenge closed at other device.");
//
//                        // TODO
//                    } else {
//                        // TODO Other
//                        System.out.println("■Other.");
//                    }
//                }
//            } else if (Objects.equals(instagramLoginResult.getError_type(), "bad_password")) {
//                System.out.println("■Bad password.");
//                System.out.println(instagramLoginResult.getMessage());
//            } else if (Objects.equals(instagramLoginResult.getError_type(), "rate_limit_error")) {
//                System.out.println("■Too many request.");
//                System.out.println(instagramLoginResult.getMessage());
//            } else if (Objects.equals(instagramLoginResult.getError_type(), "invalid_parameters")) {
//                System.out.println("■Invalid parameter.");
//                System.out.println(instagramLoginResult.getMessage());
//            } else if (Objects.equals(instagramLoginResult.getError_type(), "needs_upgrade")) {
//                System.out.println("■App upgrade needed.");
//                System.out.println(instagramLoginResult.getMessage());
//            } else if (Objects.equals(instagramLoginResult.getError_type(), "sentry_block")) {
//                System.out.println("■Sentry block.");
//                System.out.println(instagramLoginResult.getMessage());
//            } else if (Objects.equals(instagramLoginResult.getError_type(), "inactive user")) {
//                System.out.println("■Inactive user.");
//                System.out.println(instagramLoginResult.getMessage());
//            } else if (Objects.equals(instagramLoginResult.getError_type(), "unusable_password")) {
//                System.out.println("■Unusable password.");
//                System.out.println(instagramLoginResult.getMessage());
//            } else if (instagramLoginResult.getTwo_factor_info() != null) {
//                System.out.println("■Two factor authentication needed.");
//                System.out.println(instagramLoginResult.getMessage());
//
//                // If do re-authentication
//                if (doReAuthentication) {
//                    // Two factor authentication
//                    InstagramLoginResult twoFactorInstagramLoginResult = instagram4j.login("351896");
//
//                    // Check login result
//                    checkInstagramLoginResult(instagram4j, twoFactorInstagramLoginResult, false);
//                }
//            } else if (Objects.equals(instagramLoginResult.getMessage(),
//                    "Please check the code we sent you and try again.")) {
//                System.out.println("■Invalid security code.");
//                System.out.println(instagramLoginResult.getMessage());
//            } else if (Objects.equals(instagramLoginResult.getMessage(),
//                    "This code has expired. Go back to request a new one.")) {
//                System.out.println("■Security code has expired.");
//                System.out.println(instagramLoginResult.getMessage());
//            } else if (instagramLoginResult.getChallenge() != null) {
//                System.out.println("■Challenge : " + instagramLoginResult.getChallenge());
//                System.out.println(instagramLoginResult.getMessage());
//                if (instagramLoginResult.getChallenge().getLock() != null
//                        && instagramLoginResult.getChallenge().getLock()) {
//                    // Login locked
//                    System.out.println("■Login locked.");
//                    return "fail";
//                } else {
//                    // Logged in user exists, or maybe showing security code
//                    // view on other device
//                    System.out.println("■Logged in user exists, or maybe showing security code view on other device.");
//                    return "fail";
//                }
//            } else {
//                System.out.println("■Unknown error : " + instagramLoginResult.getError_type());
//                System.out.println(instagramLoginResult.getMessage());
//                return "fail";
//            }
//        } else {
//            System.out.println("■Unknown status : " + instagramLoginResult.getStatus());
//            System.out.println(instagramLoginResult.getMessage());
//            return "fail";
//        }
//        return "fail";
//    }

    }
    private static class UsernameJSON{
        private String username;

        public UsernameJSON() {
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }

    private static class LoginWrapper {
        private String username;
        private String password;

        public LoginWrapper(String username, String password) {
            this.username = username;
            this.password = password;
        }

        public LoginWrapper() {
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
