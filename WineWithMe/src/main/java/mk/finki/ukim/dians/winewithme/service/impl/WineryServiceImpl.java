package mk.finki.ukim.dians.winewithme.service.impl;

import lombok.AllArgsConstructor;
import mk.finki.ukim.dians.winewithme.model.User;
import mk.finki.ukim.dians.winewithme.model.Winery;
import mk.finki.ukim.dians.winewithme.repository.UserRepository;
import mk.finki.ukim.dians.winewithme.repository.WineryRepository;
import mk.finki.ukim.dians.winewithme.service.WineryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class WineryServiceImpl implements WineryService {
    private final WineryRepository wineryRepository;
    private final UserRepository userRepository;

    /**
     * Finds all wineries
     *
     * @return all wineries
     */

    @Override
    public List<Winery> getAllWineries() {
        return wineryRepository.findAll();
    }

    /**
     * finds a winery by ID
     *
     * @param id
     * @return the winery with the ID wanted
     */

    @Override
    public Optional<Winery> findById(Long id) {
        return wineryRepository.findById(id);
    }

    /**
     * Calculates the new rating of the winery
     *
     * @param score
     * @param cntReview
     * @return the new rating
     */

    private Double getNewScore(Double score, Double cntReview) {
        double newScore = score / cntReview;
        long number = Math.round(newScore * 100);
        newScore = number / 100.00;
        return newScore;
    }

    /**
     * Function for adding review
     *
     * @param winery
     * @param user
     * @param review
     */

    @Override
    public void addReview(Winery winery, User user, Integer review) {
        Double score = winery.scoreCalculate();
        double cntReview = Double.parseDouble(winery.getReviewsCount());
        if (review == null) {
            if (user.getWineryReview().containsKey(winery)) {

                cntReview -= 1;
                score -= user.getWineryReview().get(winery);
                user.getWineryReview().remove(winery);
                winery.setReviewsCount(Double.toString(cntReview));


                winery.setTotalScore(String.valueOf(getNewScore(score, cntReview)));

                wineryRepository.save(winery);
                userRepository.save(user);
            }
        } else {
            if (!user.getWineryReview().containsKey(winery)) {
                cntReview += 1;
                score += review;
                winery.setReviewsCount(Double.toString(cntReview));


                winery.setTotalScore(String.valueOf(getNewScore(score, cntReview)));

                user.getWineryReview().put(winery, review);
                wineryRepository.save(winery);
                userRepository.save(user);
            } else {
                score -= user.getWineryReview().get(winery);
                score += review;
                user.getWineryReview().remove(winery);
                userRepository.save(user);

                winery.setTotalScore(String.valueOf(getNewScore(score, cntReview)));

                user.getWineryReview().put(winery, review);

                userRepository.save(user);
                wineryRepository.save(winery);
            }

        }

    }

    /**
     * Filters wineries based on the provided city and/or title.
     *
     * @param city
     * @param title
     * @return A list of wineries that match the specified criteria.
     */

    @Override
    public List<Winery> filter(String city, String title) {
        if (title != null && city != null) {
            return wineryRepository.findWineriesByCityEqualsIgnoreCaseAndTitleContainsIgnoreCase(city, title);
        } else if (title != null) {
            return wineryRepository.findWineriesByTitleContainsIgnoreCase(title);
        } else if (city != null) {
            return wineryRepository.findWineriesByCityEqualsIgnoreCase(city);
        }
        return wineryRepository.findAll();

    }
}
