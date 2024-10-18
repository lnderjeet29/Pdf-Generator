<h1>Dynamic PDF Invoice Generator using Spring Boot and iText</h1>

<p>This project is a Spring Boot application that generates a dynamic <strong>PDF invoice</strong> using data provided via a REST API. The invoice contains seller, buyer, and item details, all organized into tables and neatly formatted using the <strong>iText</strong> library.</p>

<h2>Features</h2>
<ul>
  <li>Accepts seller, buyer, and item data via a REST API.</li>
  <li>Dynamically generates a well-formatted PDF invoice.</li>
  <li>Stores the generated PDF in a publicly accessible directory (<code>C:/Users/Public/Documents/</code>).</li>
  <li>Allows the user to download the generated PDF via a REST API.</li>
  <li>Easily configurable for different platforms and directories.</li>
</ul>

<h2>Technologies</h2>
<ul>
  <li><strong>Spring Boot</strong>: For creating RESTful APIs.</li>
  <li><strong>iText</strong>: For generating PDF documents.</li>
  <li><strong>Maven</strong>: For dependency management and building the project.</li>
  <li><strong>Java</strong> (Version 8 or later).</li>
</ul>

<h2>Project Structure</h2>
<pre>
src
├── main
│   ├── java
│   │   └── com.example.pdfgenerator
│   │       ├── controller
│   │       │   └── PdfController.java      # Handles API requests
│   │       ├── model
│   │       │   └── PdfRequest.java         # Represents request data (Seller, Buyer, Items)
│   │       ├── service
│   │       │   └── PdfService.java         # Service class to generate PDF
│   └── resources
│       └── application.properties          # Configuration
└── test
    └── java
        └── com.example.pdfgenerator
            └── PdfControllerTest.java      # Unit tests for the controller
</pre>

<h2>How to Use</h2>

<h3>1. Clone the repository</h3>
<pre>
git clone https://github.com/your-username/pdf-generator.git
cd pdf-generator
</pre>

<h3>2. Build the project</h3>
<p>Ensure you have <strong>Maven</strong> installed. Run the following command to build the project:</p>
<pre>
mvn clean install
</pre>

<h3>3. Run the Application</h3>
<p>Start the Spring Boot application using:</p>
<pre>
mvn spring-boot:run
</pre>

<h3>4. Testing the API</h3>
<p>Use <strong>Postman</strong> or <strong>Curl</strong> to interact with the API.</p>

<h4>Generate PDF</h4>
<p>Send a <strong>POST</strong> request to generate a PDF. The data you provide in the request body will be used to generate the invoice.</p>
<ul>
  <li><strong>Endpoint</strong>: <code>/api/pdf/generate</code></li>
  <li><strong>Method</strong>: <code>POST</code></li>
  <li><strong>Request Body</strong>:</li>
</ul>

<pre>
{
  "seller": "ABC Pvt. Ltd.",
  "sellerGstin": "22ABCDE1234Z5A1",
  "sellerAddress": "New Delhi, India",
  "buyer": "XYZ Computers",
  "buyerGstin": "29ABCDE9876Z1A2",
  "buyerAddress": "Bangalore, India",
  "items": [
    {
      "name": "Laptop",
      "quantity": "1 Nos",
      "rate": 55000.00,
      "amount": 55000.00
    },
    {
      "name": "Mouse",
      "quantity": "5 Nos",
      "rate": 200.00,
      "amount": 1000.00
    }
  ]
}
</pre>

<p><strong>Response</strong>:</p>
<pre>
{
  "message": "PDF generated successfully.",
  "downloadUrl": "/api/pdf/download?file=C:/Users/Public/Documents/invoice_1697241905001.pdf"
}
</pre>

<h4>Download the PDF</h4>
<p>Use the download URL provided in the response above to download the generated PDF:</p>
<ul>
  <li><strong>Endpoint</strong>: <code>/api/pdf/download?file=C:/Users/Public/Documents/invoice_1697241905001.pdf</code></li>
  <li><strong>Method</strong>: <code>GET</code></li>
</ul>

<h2>Example Generated PDF</h2>
<p>The PDF will look similar to the following:</p>

<pre>
<img src="https://github.com/user-attachments/assets/ee308f9c-4b9d-410b-807d-9bb67a4aa90e" alt="GrowthX Logo" width="400" height="200"/> <img src="https://github.com/user-attachments/assets/b11ba97d-9bbb-4c06-9645-276309e17ae1" alt="GrowthX Logo" width="100" height="100"/>
</pre>

<h2>Configuration</h2>
<p><strong>File Storage Path</strong>: The generated PDF will be stored in the directory <code>C:/Users/Public/Documents/</code>. You can configure this path if needed.</p>

<p>If you'd like to change the path, modify the directory in <code>PdfService.java</code>:</p>

<pre>
String directoryPath = "C:/Users/Public/Documents"; // Default public location
</pre>

<h3>Cross-Platform Support</h3>
<p>To ensure compatibility on other operating systems (Linux, macOS), this path can be modified by detecting the OS dynamically:</p>

<pre>
String directoryPath = System.getProperty("user.home") + File.separator + "Documents";
</pre>


