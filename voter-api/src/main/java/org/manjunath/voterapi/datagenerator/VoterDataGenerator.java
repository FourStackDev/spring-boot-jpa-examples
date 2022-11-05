package org.manjunath.voterapi.datagenerator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.manjunath.voterapi.codetype.GenderType;
import org.manjunath.voterapi.model.Address;
import org.manjunath.voterapi.model.Voter;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;

public class VoterDataGenerator {

    private static final List<String> maleFirstNames = List.of(
            "Manjunath", "Manoj", "Sunil", "Vinay", "Raju", "Kiran", "Sachin",
            "Ramesh", "Keerthan", "Kushal", "Kishan"
    );

    private static final List<String> femaleFirstNames = List.of(
            "Deepika", "Deepa", "Likitha", "Pooja", "Ramya", "Soumya", "Navya"
    );

    private static final String[] surNames = {
            "Gowda", "Reddy", "Shetty", "A", "S", "T", "R"
    };

    private static final String[] localities = {
            "MG Road", "BTM Layout", "Narayanapura", "Gangamma Layout", "Chandra Layout",
            "Basaveswara nagar", "Kengunte"
    };

    private static final String[] addrLine1 = {
            "1st cross", "2nd cross", "3rd cross", "4th cross", "11th cross", "15th cross"
    };

    private static final String[] addrLine2 = {
            "3rd main", "11th main", "29th main", "6th main", "8th main", "4th main", "18th main"
    };

    private static final String[] talluks = {
            "Sidlagatta", "Rabakavi", "Kampli", "Bengaluru", "Mandya", "Mysore", "Kadooru",
            "Kolar", "Chikkaballapur"
    };

    private static final String[] districts = {
            "Kolar", "Chikkaballapur", "Bagalakote", "Bellary", "Bengaluru", "Mysore", "Mandya", "ChikMagaluru"
    };

    private static final String[] zipCodes = {
            "562102", "562108", "560029", "560016", "560005", "562341", "567213", "562987"
    };


    private static int count = 1;
    private static String VOTER_ID = "VOTER-0000";
    private static Map<GenderType, List<String>> map;
    private static Random random;
    private static ObjectMapper objectMapper;

    static {
        map = new HashMap<>();
        map.put(GenderType.MALE, maleFirstNames);
        map.put(GenderType.FEMALE, femaleFirstNames);

        random = new Random();
        objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        String url = "http://localhost:8080/voter-app/api/v1/voter";
        HttpClient httpClient = HttpClient.newHttpClient();

        boolean isMaleVoterGenerated = false;
        int i = 0;
        while (i < 200) {
            Voter voter = null;
            LocalDate randomDate = generateRandomDate(1992, 2005);
            if (isMaleVoterGenerated) {
                voter = getVoter(map.get(GenderType.FEMALE), randomDate, GenderType.FEMALE);
            } else {
                voter = getVoter(map.get(GenderType.MALE), randomDate, GenderType.MALE);
            }

            postVoterToTarget(url, httpClient, voter);
            isMaleVoterGenerated = !isMaleVoterGenerated;
            i++;
        }

    }

    private static Voter getVoter(List<String> names, LocalDate randomDate, GenderType genderType) {
        return generateVoter(
                names.get(random.nextInt(names.size())),
                surNames[random.nextInt(surNames.length)],
                randomDate,
                genderType
        );
    }

    private static void postVoterToTarget(String url, HttpClient httpClient, Voter voter) throws IOException, InterruptedException {
        System.out.println("Posting to the target :"+url);
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(voter)))
                .build();


        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.statusCode());
    }

    private static Voter generateVoter(String firstName, String lastName, LocalDate dob, GenderType gender) {
        return Voter.builder()
                .id(VOTER_ID + (count++))
                .firstName(firstName)
                .lastName(lastName)
                .birthDate(dob)
                .gender(gender)
                .address(generateAddress())
                .build();
    }

    private static Address generateAddress() {
        return Address.builder()
                .doorNo(String.valueOf(random.nextInt(200)))
                .streetAddr1(addrLine1[random.nextInt(addrLine1.length)])
                .streetAddr2(addrLine2[random.nextInt(addrLine2.length)])
                .locality(localities[random.nextInt(localities.length)])
                .talluk(talluks[random.nextInt(talluks.length)])
                .district(districts[random.nextInt(districts.length)])
                .state("Karnataka")
                .country("India")
                .zipcode(zipCodes[random.nextInt(zipCodes.length)])
                .build();
    }

    /**
     * Generates the Random Date between provided Years.
     *
     * @return
     */
    private static LocalDate generateRandomDate(int fromYear, int toYear) {
        long minDay = LocalDate.of(fromYear, 01, 01).toEpochDay();
        long maxDay = LocalDate.of(toYear, 12, 31).toEpochDay();
        long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);
        LocalDate randomDate = LocalDate.ofEpochDay(randomDay);
        return randomDate;
    }


}
