package ArtGalleryVisitior;

public abstract class ArtGalleryVisitor {
    protected int visitorId, visitCount, cancelCount, buyCount;
    protected String fullName, gender, contactNumber, registrationDate, ticketType, cancellationReason, artworkName;
    protected double ticketCost, rewardPoints, finalPrice, discountAmount, artworkPrice, refundableAmount;
    protected final int cancelLimit = 3;
    protected boolean isActive, isBought;  

    //constructor
    public ArtGalleryVisitor(int visitorId, String fullName, String gender, String contactNumber,String registrationDate, double ticketPrice, String ticketType) {
        // visitor information (identity)
        this.visitorId = visitorId; 
        this.fullName = fullName; 
        this.gender = gender;
        this.contactNumber = contactNumber;
        this.registrationDate = registrationDate;
        this.ticketCost = ticketPrice; 
        this.ticketType = ticketType;

        // ticket and financial details
        this.visitCount = 0;
        this.cancelCount = 0;
        this.buyCount = 0;
        this.rewardPoints = 0;
        this.finalPrice = 0;
        this.discountAmount = 0;
        this.refundableAmount = 0;

        // activity tracking
        this.isActive = false;
        this.isBought = false;
        this.cancellationReason = "";
        this.artworkName = null;
    }
 
    public void logVisit() {
        visitCount++;  // increase visit count
        isActive = true;  // mark the visitor as active
    }

    //Abstract methods
    public abstract String buyProduct(String name, double price);
    public abstract double calculateDiscount();
    public abstract double calculateRewardPoint();
    public abstract String cancelProduct(String name, String reason);
    public abstract void generateBill();
    
    //display visitor info
    public void display() {
        System.out.println("ID: " + visitorId + " Name: " + fullName + " Visits: " + visitCount +" Reward: " + rewardPoints + " Bought: " + isBought);
    }

    //  Getter methods (needed for GUI) 
    public int getVisitorId() { 
        return visitorId; 
    }
    public String getFullName() { 
        return fullName; 
    }
    public String getGender() {
        return gender; 
    }
    public String getContactNumber() {
        return contactNumber; 
    }
    public String getRegistrationDate() {
        return registrationDate; 
    }
    public String getTicketType() {
        return ticketType; 
    }
    public double getTicketPrice() { 
        return ticketCost; 
    }
    public String getCancellationReason() {
        return cancellationReason; 
    }
    public String getArtworkName() {
        return artworkName; 
    }
    public double getArtworkPrice() { 
        return artworkPrice; 
    }
    public boolean isActive() {
        return isActive; 
    }
    public boolean isBought() { 
        return isBought; 
    }

    //Setter method
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }
}
