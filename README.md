# a6TestCollection
A collection of JUnit tests for A6

To contribute to this test collection, first create a "fork" of this repository by pressing the "Fork" button that should be on the top right of the repository web interface on GitHub. This will allow you to create a private copy of the repository in your GitHub account that you have write permission for.

Once you have a forked copy of this repo, you can clone it into Eclipse.

Please put all of your tests into a package with the following name pattern:
```
a6test.onyen
```

where "onyen" should be replaced with your specific Onyen. 

Your tests should be written using JUnit 4 and assuming that the code to be tested is in the package ```a6```.

Within your test package, please put all of your tests within a class called ```A6Tests```. You may create additional helper classes within your package as necessary.

Push your code to your forked copy of the repo and then through the GitHub web interface to your forked copy request a "pull" request. 

To make a pull request, log into GitHub, go to your forked copy of the repository, press the button for "Pull Request". On the next screen, you should see a way to select which branch you are making the pull request to and from on either side of a left facing arrow. Make sure the left hand side of the arrow is selecting the master branch of the base fork and the right hand side of the arrow is selecting whatever branch you pushed to on your private fork. Then press the "Create Pull Request" button.

A few things to watch out for:
* If your tests rely on any other classes (like for example an implementation of ROIObserver), be sure those are located in your test package and not a6.
* Avoid including .classpath, .profile, or .gitignore in what you submit to the pull request.





