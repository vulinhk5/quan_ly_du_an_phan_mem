package Core;

public class Document {
    private String documentId;
    private String title;
    private String author;
    private String publisher;
    private String description;
    private String isbn;
    private String language;
    private int pageCount;
    private String coverImageUrl;
    private double averageRating;
    private int amount;

    // Constructor
    public Document(String documentId, String title, String author, String publisher, String description,
        String isbn, String language, int pageCount, String coverImageUrl,
        double averageRating, int amount) {
        this.documentId = documentId;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.description = description;
        this.isbn = isbn;
        this.language = language;
        this.pageCount = pageCount;
        this.coverImageUrl = coverImageUrl;
        this.averageRating = averageRating;
        this.amount = amount;
    }

    // Getters and Setters
    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public String getCoverImageUrl() {
        return coverImageUrl;
    }

    public void setCoverImageUrl(String coverImageUrl) {
        this.coverImageUrl = coverImageUrl;
    }

    public double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Document{" +
            "documentId='" + documentId + '\'' +
            ", title='" + title + '\'' +
            ", author='" + author + '\'' +
            ", publisher='" + publisher + '\'' +
            ", description='" + description + '\'' +
            ", isbn='" + isbn + '\'' +
            ", language='" + language + '\'' +
            ", pageCount=" + pageCount +
            ", coverImageUrl='" + coverImageUrl + '\'' +
            ", averageRating=" + averageRating +
            ", amount=" + amount +
            '}';
    }
}
