# lol_notes
*Tracking own deaths in a live-game and log it to figure out the reasons for them to happen. This is just the beginning for more interesting ideas.*

## Project Structure
```
.
├── backend
│   └── tracking
├── frontend
│   └── cli
├── logs
└── script
```
Currently there is only one backend module which is `tracking` module. Tbh, this module contains some services that shouldn't be in it. Like
getting the match history of a player, Also I have my doubt about the persistence part, as probably this module will be downloaded with the 
destkop app in local. **This will be refactored in one of the future PRs**. 

I made the decision of choosing to develop a *cli* module before creating a more complex *desktop app*. As it will make the project somehow usefull in a shorter time
and also because it is much faster to interact with if the user currently playing, no need to scroll all over a page.

You just need to know the command to run and if you type fast it will fill much more faster to use the cli than the graphical interface that 
also my have delays and lacks in the performance departement compared to cli which is normal. I would assume that the graphical interface will
have more features than the cli thought. 

### Technologies
I started this project with `Java` and `Spring` as I got introduced to them in the uni. I loved working with Spring and wanted to improve my 
knowledge in it. Besides that, it is actually a good option, as Java is plateform-independant and mature, and also with Spring providing the
Scalability and Modularity. 

Concerning the CLI. I chose `Go`, because compared to the other popular alternative, I found out that Go is efficient and fast, while keeping
a simple and straighforward syntax. `Go` supports cross-compilation, allowing developers to build CLI tools for multiple platforms from a single codebase.
And also for the simple reason that I want to work with a language I never used. 


## How To Use 
In the `script/` folder, you will find multiple bash scripts to run:
```
.
├── build_script  # For building all the modules 
├── run_script    # For running all the modules
└── stop_script  # For stopping all the running modules. 
```
- You will find logs of your builds and runs in the generated log folder.
- Don't forget to check if you can run the scripts `ls -l` , in case you cannot -> `chmod +x [file_name]`






*🔄 LastUpdate: 04/06/2023* 

