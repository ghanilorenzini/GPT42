# GPT42

#project 2 GPT42 Ghani, Akram

How to contribute to this project
Down here youll find a detailed guide on how to get started with writing code for this project.

1. How to clone the repository
Open Git Bash and redirect to the folder you wish to export the project to locally.
Use the git clone https://github.com/joren-dev/ScheepsOfferte-Project-SE.git and wait till it finishes.
Test connection with remote repo with: git status.
Open the project folder inside IntelliJ, and try to run the code.
2. How to create a new branch
Switch to main branch: git checkout main
Ensure local branch is up to date: git pull
Create new branch: git checkout -b branch_name. Make sure to name the branch according to its purpose.
3. How to commit to branch
Start writing a base for your new branch, when ready continue the steps
Add all untracked changes: git add .
List the changes, and double check if correct: git status
Commit the changes: git commit -m "tag: useful short commit info", find more about tags here.
Push the changes to remote branch: git push
4. How to merge branch
Go to the branch you wish to be merged with another one: git checkout branch_merged_into_current
Use command: git merge branch_merged_into_current -m "merge: useful merge message"
