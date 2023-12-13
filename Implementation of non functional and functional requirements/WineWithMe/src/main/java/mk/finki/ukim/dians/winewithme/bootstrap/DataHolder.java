package mk.finki.ukim.dians.winewithme.bootstrap;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import mk.finki.ukim.dians.winewithme.model.Winery;
import mk.finki.ukim.dians.winewithme.repository.WineryRepository;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@AllArgsConstructor
public class DataHolder {
    public static List<Winery> list0fWineries = new ArrayList<>();
    private final WineryRepository wineryRepository;

    @PostConstruct
     void init() {
        inputWineries();
    }

     void inputWineries() {
        if(wineryRepository.count()==0){
            BufferedReader bufferedReader = null;
            try {
                bufferedReader = new BufferedReader(new FileReader("../../Data filtering and requirements analysis/FilterWineryData/output.csv"));
                list0fWineries = bufferedReader.lines().map(Winery::createWinery).toList().stream().skip(1).toList();
                wineryRepository.saveAll(list0fWineries);
            } catch (Exception e) {
            }
        }

    }
}