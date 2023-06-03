package project1;

import java.util.List;
import java.util.Scanner;

public class BookMapperFrontend implements IBookMapperFrontend {
    // public String isbn;
    // public String Title;
    private FDBookMapperBackend backend;
    private FDIISBNValidator validator;
    private Scanner userInputScanner;
    

    BookMapperFrontend(Scanner userInputScanner, FDBookMapperBackend backend, FDIISBNValidator validator) {
        this.userInputScanner = userInputScanner;
        this.backend = backend;
        this.validator = validator;

    }
 


    /**
     * This method starts the command loop for the frontend, and will
     * terminate when the user exists the app.
     */
    public void runCommandLoop() 
    {
        System.out.println("Welcome to the Book Mapper Application !");
        System.out.println("x+x+x+x+x+x+x+x+x+x+x+x+x+x+x+x+x+x+x+x");
        String b;
        while(true)
        {
            displayMainMenu();
            b = userInputScanner.next(); 
            switch (b) 
            {
                case "1":
                    
                    isbnLookup();
                    break;
                case "2":
                    
                    titleSearch();
                    break;
                case "3":
                    System.out.println("You are in the Set Author Filter Menu:");
                    System.out.println("Author name must currently contain:" + backend.getAuthorFilter());
                    System.out.print("Enter a new string for author names to contain (none for any): ");
                    backend.authorfilter = userInputScanner.next();
                    if(backend.authorfilter.equals("none"))
                    {
                        backend.resetAuthorFilter();
                    }
                    else
                    {
                        backend.setAuthorFilter(backend.authorfilter);   
                    }
                    break;
                case "4":
                    System.out.println("GoodBye!");
                    System.exit(0);
                }     
            
            
        }

    }

    // to help make it easier to test the functionality of this program,
    // the following helper methods will help support runCommandLoop():

    // prints command options to System.out
    public void displayMainMenu() {
        System.out.println("You are in the Main Menu:");
        System.out.println("    1) Lookup ISBN");
        System.out.println("    2) Search by Title Word");
        System.out.println("    3) Set Author Name Filter");
        System.out.println("    4) Exit Application");

    }

    // displays a list of books
    public void displayBooks(List<IBook> books) {
        for (int i = 0; i < books.size(); i++) {

            System.out.println(i + " \"" + books.get(i).getTitle() + "\" by" + books.get(i).getAuthors() + " , ISBN:"
                    + books.get(i).getISBN13());
        }
        System.out.println("Matches (Author Filter:" + backend.getAuthorFilter() + " " + books.size() + " of "
                + backend.getNumberOfBooks() + ")");

    }

    // reads word from System.in, displays results

    public void isbnLookup() {
    	System.out.println("You are in the Lookup ISBN Menu:");
        System.out.print("  Enter ISBN to look up:");
        String isbn = " ";
        isbn = userInputScanner.next();
        if (validator.validate(isbn)) {
            IBook a = backend.getByISBN(isbn);
            System.out.println("1. \"" + a.getTitle() + "\" by " + a.getAuthors() + " , ISBN:" + a.getISBN13());
        
        }
      
        
    }

    // reads author name from System.in, displays results
    public void titleSearch() {
    	System.out.println("You are in the Search for Title Word Menu:");
        System.out.print("Enter a word to search for in book titles (none for all books):");
        String Title = userInputScanner.next();
        if(Title.equals("none"))
        {
            Title = " ";
            List<IBook> a = backend.searchByTitleWord(Title);
            displayBooks(a);
        }
        else 
        {
            List<IBook> a = backend.searchByTitleWord(Title);
            displayBooks(a);
        }
        
      
    }

    public static void main(String[] args) {
        // FDBookMapperBackend a = new FDBookMapperBackend();
        // FDIISBNValidator b = new FDIISBNValidator();
        new BookMapperFrontend(new Scanner(System.in), new FDBookMapperBackend() , new FDIISBNValidator()).runCommandLoop();;
       
        
    }
    

}