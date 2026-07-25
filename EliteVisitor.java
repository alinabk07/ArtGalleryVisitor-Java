package ArtGalleryVisitior;

//EliteVisitor class inherits from ArtGalleryVisitor
public class EliteVisitor extends ArtGalleryVisitor {
    private boolean assignedPersonalArtAdvisor;
    private boolean exclusiveEventAccess;
    
    //constructor
    public EliteVisitor(int visitorId, String fullName, String gender, String contactNumber, String registrationDate, double ticketPrice, String ticketType) {
        super(visitorId, fullName, gender, contactNumber, registrationDate, ticketPrice, ticketType);
        assignedPersonalArtAdvisor = false;
        exclusiveEventAccess = false;
    }
    
    //Method to assign personal art advisor if visitor has more than 5000 reward points
    public boolean assignPersonalArtAdvisor() {
        if (rewardPoints > 5000){
            assignedPersonalArtAdvisor = true;
        }
        return assignedPersonalArtAdvisor;
    }

    // Method to give exclusive event access if advisor is already assigned
    public boolean exclusiveEventAccess() {
        if (assignedPersonalArtAdvisor){
            exclusiveEventAccess = true;
        }
        return exclusiveEventAccess;
    }
    
     // Method for buying artwork
    public String buyProduct(String name, double price) {
        if (!isActive){
            return "Log visit first.";
        }
        if (isBought && name.equals(this.artworkName)){
            return "Already purchased.";
        }
        this.artworkName = name;// store artwork name
        this.artworkPrice = price; // store artwork price
        isBought = true;  // mark product as bought
        buyCount++;// increase purchase count
        
        return "Purchase successful.";
    }

    public double calculateDiscount() {
        if (!isBought){
            return 0;
        }
        discountAmount = artworkPrice * 0.40;
        finalPrice = artworkPrice - discountAmount;
        return discountAmount;
    }

    public double calculateRewardPoint() {
        if (!isBought){
            return 0;
        }
        rewardPoints += finalPrice * 10;
        return rewardPoints;
    }

    private void terminateVisitor() {
        isActive = false;
        assignedPersonalArtAdvisor = false;
        exclusiveEventAccess = false;
        visitCount = 0;
        cancelCount = 0; 
        rewardPoints = 0;
    }

    public String cancelProduct(String name, String reason) {
        if (cancelCount >= cancelLimit) { 
            terminateVisitor();
            return "Account terminated."; //too many cancellation
        }
        if (!isBought || !name.equals(this.artworkName)){
            return "No product/cannot cancel.";
        }
        
        isBought = false; 
        this.artworkName = null; 
        this.cancellationReason = reason;
        
        refundableAmount = artworkPrice - artworkPrice * 0.05;
        rewardPoints -= finalPrice * 10;
        cancelCount++;
        buyCount--;
        
        return "Cancelled. Refund: " + refundableAmount;
    }

    public void generateBill() {
        if (!isBought) { 
            System.out.println("No purchase."); 
            return; 
        }
        System.out.println("=========== BILL ===========");
        System.out.printf("Visitor ID     : %d%n", visitorId);
        System.out.printf("Visitor Name   : %s%n", fullName);
        System.out.printf("Artwork Name   : %s%n", artworkName);
        System.out.printf("Artwork Price  : %.2f%n", artworkPrice);
        System.out.printf("Discount Amount: %.2f%n", discountAmount);
        System.out.printf("Final Price    : %.2f%n", finalPrice);
        System.out.println("============================");
    }

    public void display() {
        super.display(); // call parent class display method
        System.out.println("Art Advisor: " + assignedPersonalArtAdvisor + " Exclusive Event: " + exclusiveEventAccess);
    }
    // getters for GUI
    public boolean hasAdvisor() { 
        return assignedPersonalArtAdvisor;
    }
    public boolean hasExclusiveAccess() {
        return exclusiveEventAccess; 
    }
}

