package utilities;

import implementations.MyStack;
import implementations.MyQueue;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * A simple XML parser to validate the structure of an XML document.
 * It uses custom stack and queue implementations to check for tag mismatches and report errors.
 */
public class XMLParser {

    private MyStack<String> tagStack;
    private MyQueue<String> errorQueue;

    /**
     * Constructs an XMLParser instance.
     */
    public XMLParser() {
        tagStack = new MyStack<>();
        errorQueue = new MyQueue<>();
    }

    /**
     * Reads an XML file line by line and validates its structure.
     *
     * @param fileName The path to the XML file.
     */
    public void readFile(String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                validateXML(line.trim());
            }
        } catch (IOException e) {
            errorQueue.enqueue("Error reading file: " + e.getMessage());
        }
    }

    /**
     * Validates a single line of XML.
     *
     * @param line The XML line to validate.
     */
    private void validateXML(String line) {
        String[] parts = line.split("<|>"); // Split tags
        for (String part : parts) {
            if (part.isEmpty()) continue;

            if (part.startsWith("/")) { // Closing tag
                String tag = part.substring(1);
                if (tagStack.isEmpty() || !tagStack.peek().equals(tag)) {
                    errorQueue.enqueue("Mismatched or unexpected closing tag: </" + tag + ">");
                } else {
                    tagStack.pop();
                }
            } else if (part.endsWith("/")) { // Self-closing tag
                continue; // No need to add to stack
            } else if (isProcessingInstruction(part)) { // Ignore processing instructions
                continue;
            } else { // Opening tag
                tagStack.push(part);
            }
        }
    }

    /**
     * Checks if a string is a processing instruction (e.g., <?xml version="1.0"?>).
     *
     * @param part The string to check.
     * @return True if it is a processing instruction, false otherwise.
     */
    private boolean isProcessingInstruction(String part) {
        return part.startsWith("?");
    }

    /**
     * Prints the results of the XML validation.
     * If there are errors, they are printed in the order they occurred.
     * If no errors are found, a success message is displayed.
     */
    public void printErrors() {
        if (errorQueue.isEmpty() && tagStack.isEmpty()) {
            System.out.println("The XML is valid.");
        } else {
            while (!errorQueue.isEmpty()) {
                System.out.println(errorQueue.dequeue());
            }
            while (!tagStack.isEmpty()) {
                System.out.println("Unclosed tag: <" + tagStack.pop() + ">");
            }
        }
    }

    /**
     * The main method to run the XMLParser from the command line.
     *
     * @param args Command-line arguments. The first argument should be the XML file path.
     */
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: java XMLParser <file.xml>");
            return;
        }

        XMLParser parser = new XMLParser();
        parser.readFile(args[0]);
        parser.printErrors();
    }
}
