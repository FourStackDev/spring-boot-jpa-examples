package util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.manjunath.voterapi.model.Voter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EntityGenerator {

    private static final String VOTER_LIST_FILEPATH = "json-files/voter-list.json";
    private static final String VOTER_SINGLE_FILEPATH = "json-files/voter-single.json";

    private static ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
    }


    public static List<Voter> getVoters() {
        Voter[] voters = {};

        try {
            voters = objectMapper.readValue(
                    TestUtility.getFileContent(VOTER_LIST_FILEPATH),
                    Voter[].class
            );
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return Arrays.asList(voters);
    }

    public static Voter getVoter() {
        Voter voter = null;
        try {
            voter = objectMapper.readValue(
                    TestUtility.getFileContent(VOTER_SINGLE_FILEPATH),
                    Voter.class
            );
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return voter;
    }
}
