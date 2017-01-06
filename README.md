# CompareMe
CompareMe is an application that finds duplicate files in a specified directory. This is used to easily delete duplicate files. CompareMe is written in Java utilizing JavaFX. 

CompareMe generates a hash for each file in the given directory. Files with the same hash (hash collisions) will be marked as a duplicate. Duplicate hashes imply that there is a duplicate file. Stronger hash functions may take more time to compute, but will also decrease the chance of finding an incorrect/false collision. Using the Second Check option can also mitigate false collisions.

## Requirements
CompareMe is powered by Java and written using Oracle JDK 1.8.0_111. JRE 8 or higher is required to run this application. This application may not work with OpenJDK due to the usage of JavaFX. Oracle JDK implements some JavaFX items that are not available in OpenJDK.
 
## Building CompareMe
 1. Import CompareMe as a project in IntelliJ.
 2. Go to Build > Build Artifacts
 3. Select Build
 4. Build output should be located at `/out/artifacts/CompareMe/`
 
## Running CompareMe
 1. Run `CompareMe.jar`
 2. Select a directory using the directory chooser
 3. Select applicable options
 4. Select "Start Comparing!"
 5. CompareMe will continue based on the options selected
    1. If Auto Delete is selected, then duplicates will automatically be deleted
    2. If Auto Delete is not selected, the manual deleter will show up. Simply check off and delete any files

## CompareMe Options
 * `Recursive` - Checks for all files in all sub-directories of the selected directory
 * `Second Check` - Prevents false collisions by doing a second check over all the files using byte-by-byte comparisons.