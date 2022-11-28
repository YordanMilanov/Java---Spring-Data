package bg.softuni.gamestore.domain.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;

public class GameDTO {


    private String title;

    private String trailerId;

    private String imageUrl;

    private float size;

    private BigDecimal price;

    private String description;

    private LocalDate releaseDate;

    public GameDTO(String title,
                   String trailerId,
                   String imageUrl,
                   float size,
                   BigDecimal price,
                   String description,
                   LocalDate releaseDate) {
        this.title = title;
        this.trailerId = trailerId;
        this.imageUrl = imageUrl;
        this.size = size;
        this.price = price;
        this.description = description;
        this.releaseDate = releaseDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTrailerId() {
        return trailerId;
    }

    public void setTrailerId(String trailerId) {
        this.trailerId = trailerId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public float getSize() {
        return size;
    }

    public void setSize(float size) {
        this.size = size;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }
}
