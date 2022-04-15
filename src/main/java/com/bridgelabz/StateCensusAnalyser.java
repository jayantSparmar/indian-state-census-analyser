package com.bridgelabz;
/**********************************************************
 * Program on User Registration Using Regex
 *
 * @author : Jayant Singh Parmar
 * @since : 15/04/2022
 **********************************************************/

/**
 * UC1 :-   Ability for the analyser to load the Indian States Census Information from a csv file
 *          - Create a StateCensusAnalyser Class to load  the State Census CSV Data
 *          - Create CSVStateCensus Class to load the CSV Data
 *          - Use Iterator to load the data
 *          - Check with StateCensusAnalyser to ensure no of record matches.
 * TC1.1 :- Given the States Census CSV file, Check to ensure the Number of Record matches
 *          - This is a Happy Test Case where the records are checked
 * TC1.2 :- Given the State Census CSV File if incorrect Returns a custom Exception
 *          - This is a Sad Test Case to verify if the exception is raised.
 * TC1.3 :- Given the State Census  CSV File when correct but type incorrect Returns a custom Exception
 *          - This is a Sad Test Case to verify if the type is incorrect then exception is raised.
 *
 * TC1.4 :- Given the State Census CSV File when correct but delimiter incorrect Returns a custom Exception
 *          - This is a Sad Test Case to verify if the file delimiter is incorrect then exception is raised.
 *
 * TC1.5 :- Given the State Census CSV File when correct but csv header incorrect Returns a custom Exception
 *          - This is a Sad Test Case to verify if the header is incorrect then exception is raised.
 *==================================================================================================================
 *
 *  UC2  :- Ability for the analyser to load the Indian States Code Information from a csv  file
 *          - Create CSVStates Class to load the CSV Data
 *          - Use Iterator to load the data
 *          - Check with StateCensusAnalyser to ensure number of record matches
 *
 * TC2.1 :- Given the States Census CSV file, Check to ensure the Number of Record matches
 *          - This is a Happy Test Case where the records are checked
 *
 * TC2.2 :- Given the State Census CSV File if incorrect Returns a custom Exception
 *          - This is a Sad Test Case to verify if the exception is raised.
 *
 * TC2.3 :- Given the State Census  CSV File when correct but type incorrect Returns a custom Exception
 *          - This is a Sad Test Case to verify if the type is incorrect then exception is raised.
 *
 * TC2.4 :- Given the State Census CSV File when correct but delimiter incorrect Returns a custom Exception
 *          - This is a Sad Test Case to verify if the file delimiter is incorrect then exception is raised.
 *
 * TC2.5 :- Given the State Census CSV File when correct but csv header incorrect Returns a custom Exception
 *          - This is a Sad Test Case to verify if the header is incorrect then exception is raised.
 *
 */

/**
 * import all classes
 */

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;


public class StateCensusAnalyser {
    private static List<CSVStateCensus> csvFileList = new ArrayList<>();
    private static List<CSVStates> csvStateCodeList = new ArrayList<>();

    public int loadIndianCensusData(String csvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
            if (!csvFilePath.endsWith(".csv"))
                throw new CensusAnalyserException("File must be in CSV format", CensusAnalyserException.ExceptionType
                        .INCORRECT_FILE_FORMAT);

            CsvToBeanBuilder<CSVStateCensus> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
            csvToBeanBuilder.withType(CSVStateCensus.class);
            csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
            CsvToBean<CSVStateCensus> csvToBean = csvToBeanBuilder.build();
            Iterator<CSVStateCensus> censusCSVIterator = csvToBean.iterator();

            while (censusCSVIterator.hasNext()) {
                csvFileList.add(censusCSVIterator.next());
            }
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType
                    .CENSUS_FILE_PROBLEM);
        } catch (NullPointerException e) {
            throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        } catch (RuntimeException e) {
            throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType
                    .CSV_FILE_INTERNAL_ISSUES);
        }

        return csvFileList.size();
    }

    public int loadIndianStateCode(String csvFilePath) throws CensusAnalyserException {
        try {
            if (!csvFilePath.endsWith(".csv"))
                throw new CensusAnalyserException("File must be in CSV format", CensusAnalyserException.ExceptionType
                        .INCORRECT_FILE_FORMAT);
            Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));
            CsvToBeanBuilder<CSVStates> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
            csvToBeanBuilder.withType(CSVStates.class);
            csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
            CsvToBean<CSVStates> csvToBean = csvToBeanBuilder.build();
            Iterator<CSVStates> stateCodeIterator = csvToBean.iterator();

            while (stateCodeIterator.hasNext()) {
                csvStateCodeList.add(stateCodeIterator.next());
            }
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.
                    CENSUS_FILE_PROBLEM);
        } catch (RuntimeException e) {
            throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.
                    CSV_FILE_INTERNAL_ISSUES);
        }
        return csvStateCodeList.size();
    }
}