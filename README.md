# SE306 Project 2 Team 11
A branch-and-bound type algorithm that solves tasks scheduling on multiple processors optimally for small input graphs.

## How to Run
Make sure the file is in `.dot` format with the correct extension name

Then in Command Line:
```
java -jar scheduler.jar <Path to .dot File> <Processor Number> [OPTIONS]
```
### Optional Arguments
 - `-p <Core Number>` Use <Core Number> cores for execution in parallel (Not Implemented Yet)
 - `-v` Visualize the search
 - `-o <File Name>` output file is named <File Name>
 
 ## Build With
  ### Data Structure
  - Java
  
 ### GUI
  - javafx
  - Graphviz
  - css
## Update
  Multi-core process is not implemented yet
