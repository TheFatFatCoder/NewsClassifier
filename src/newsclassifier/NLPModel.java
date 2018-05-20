package newsclassifier;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import newsclassifier.View;

import opennlp.tools.doccat.DoccatFactory;
import opennlp.tools.doccat.DoccatModel;
import opennlp.tools.doccat.DocumentCategorizer;
import opennlp.tools.doccat.DocumentCategorizerME;
import opennlp.tools.doccat.DocumentSample;
import opennlp.tools.doccat.DocumentSampleStream;
import opennlp.tools.ml.AbstractTrainer;
import opennlp.tools.ml.naivebayes.NaiveBayesTrainer;
import opennlp.tools.util.InputStreamFactory;
import opennlp.tools.util.MarkableFileInputStreamFactory;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;
import opennlp.tools.util.TrainingParameters;

/**
 * oepnnlp version 1.7.2
 * Training of Document Categorizer using Naive Bayes Algorithm in OpenNLP for Document Classification
 * @author www.tutorialkart.com
 */
public class NLPModel {
    private String conclusion;
    
    public NLPModel(String text){
        try {
            // read the training data
            InputStreamFactory dataIn = new MarkableFileInputStreamFactory(new File("/home/john/Desktop/NewsClassifier/NewsClassifier/src/newsclassifier/"+"en-movie-category.train"));
            //InputStreamFactory dataIn = new MarkableFileInputStreamFactory(new File("C:\\Users\\Steven\\Documents\\NetBeansProjects\\NewsClassifier\\src\\newsclassifier\\"+"en-movie-category.train"));
            ObjectStream lineStream = new PlainTextByLineStream(dataIn, "UTF-8");
            ObjectStream sampleStream = new DocumentSampleStream(lineStream);

            // define the training parameters
            TrainingParameters params = new TrainingParameters();
            params.put(TrainingParameters.ITERATIONS_PARAM, 10+"");
            params.put(TrainingParameters.CUTOFF_PARAM, 0+"");
            params.put(AbstractTrainer.ALGORITHM_PARAM, NaiveBayesTrainer.NAIVE_BAYES_VALUE);

            // create a model from traning data
            DoccatModel model = DocumentCategorizerME.train("en", sampleStream, params, new DoccatFactory());
            System.out.println("\nModel is successfully trained.");

            // save the model to local
            BufferedOutputStream modelOut = new BufferedOutputStream(new FileOutputStream("/home/john/Desktop/NewsClassifier/NewsClassifier/src/newsclassifier/"+"en-movie-classifier-naive-bayes.bin"));
            //BufferedOutputStream modelOut = new BufferedOutputStream(new FileOutputStream("C:\\Users\\Steven\\Documents\\NetBeansProjects\\NewsClassifier\\src\\newsclassifier\\"+"en-movie-classifier-naive-bayes.bin"));
            model.serialize(modelOut);
            System.out.println("\nTrained Model is saved locally at : "+"model"+File.separator+"en-movie-classifier-naive-bayes.bin");

            // test the model file by subjecting it to prediction
            DocumentCategorizer doccat = new DocumentCategorizerME(model); //the real NLP Model
            String[] docWords = text.replaceAll("[^A-Za-z]", " ").split(" ");
            double[] aProbs = doccat.categorize(docWords);

            // print the probabilities of the categories
            System.out.println("\n---------------------------------\nCategory : Probability\n---------------------------------");
            conclusion = "<html><br>---------------------------------<br>Category : Probability<br>---------------------------------<br>";
            for(int i=0;i<doccat.getNumberOfCategories();i++){
                    System.out.println(doccat.getCategory(i)+" : "+aProbs[i]);
                    conclusion += doccat.getCategory(i)+": "+aProbs[i]+"<br>";
            }
            System.out.println("---------------------------------");
            conclusion += "---------------------------------<br>";

            System.out.println("\n"+doccat.getBestCategory(aProbs)+" : is the predicted category for the given sentence.");
            conclusion += "Prediction result : "+doccat.getBestCategory(aProbs)+"<br>";
        }
        catch (IOException e) {
            System.out.println("An exception in reading the training file. Please check.");
            e.printStackTrace();
        }
    }
    
    public String getConclusion(){
        return this.conclusion;
    }
   
}