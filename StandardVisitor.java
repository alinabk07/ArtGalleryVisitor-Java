package ArtGalleryVisitior;


public class StandardVisitor extends ArtGalleryVisitor {
    private boolean isEligibleForDiscountUpgrade;
    private final int visitLimit = 5;
    private float discountPercent;
    
    //constructor
    // Calls parent constructor and sets default discount 10%
    public StandardVisitor(int visitorId, String fullName, String gender, String contactNumber, String registrationDate, double ticketPrice, String ticketType) {
        super(visitorId, fullName, gender, contactNumber, registrationDate, ticketPrice, ticketType);
        this.discountPercent = 0.10f;//start with 10% discount
        this.isEligibleForDiscountUpgrade = false;
    }
    
    // Check if visitor should get discount upgrade 
    // If visit count >= 5, discount changes from 10% , 15%
    public boolean checkDiscountUpgrade() {
        if (visitCount >= visitLimit) {
            isEligibleForDiscountUpgrade = true;
            discountPercent = 0.15f;//upgrade to 15%
        }
        return isEligibleForDiscountUpgrade;
    }
    
    //method for buying an artwork
    public String buyProduct(String name, double price) {
        if (!isActive){
            return "Log visit first.";
        }
        
        //prevent duplicate purchase of same artwork
        if (isBought && name.equals(this.artworkName)){
            return "Already purchased.";
        }
        this.artworkName = name;
        this.artworkPrice = price;
        isBought = true; 
        buyCount++;
        return "Purchase successful.";
    }
    
    //Calculate discount for purchased artwork
    public double calculateDiscount() {
        if (!isBought){
            return 0;
        }
        checkDiscountUpgrade();// check if discount should be upgraded
        discountAmount = artworkPrice * discountPercent;
        finalPrice = artworkPrice - discountAmount;
        return discountAmount;
    }

    public double calculateRewardPoint() {
        if (!isBought){
            return 0;// no reward if no purchase
        }
        rewardPoints += finalPrice * 5;
        return rewardPoints;
    }
    
     // Terminate visitor account
    // Reset all data if cancellation limit exceeded
    private void terminateVisitor() {
        isActive = false;
        isEligibleForDiscountUpgrade = false;
        visitCount = 0;
        cancelCount = 0; 
        rewardPoints = 0;
    }

    public String cancelProduct(String name, String reason) {
        if (cancelCount >= cancelLimit) { 
            terminateVisitor();
            return "Account terminated."; 
        }
        
        if (!isBought || !name.equals(this.artworkName)){
            return "No product/cannot cancel.";
        }
        
        isBought = false;
        this.artworkName = null;
        this.cancellationReason = reason;
        refundableAmount = artworkPrice - artworkPrice * 0.10;
        rewardPoints -= finalPrice * 5;
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
        super.display();//dispaly common data from parent class
        System.out.println("DiscountUpgrade: " + isEligibleForDiscountUpgrade + " %: " + discountPercent);
    }
    // getter for GUI
    public boolean isDiscountUpgraded() { 
        return isEligibleForDiscountUpgrade; 
    }
}