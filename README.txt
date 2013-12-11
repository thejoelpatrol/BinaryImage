The purpose of this program is to generate images from arbitrary binary data. It is pretty simple. It just takes whatever binary data you give it and interprets that as if it were uncompressed image data. 24 bits comprise one colored pixel.

As you resize the window, the data/image will reflow. You can save the resulting image as a PNG.

Latin text is gray and uninteresting. Any compressed data (eg zip, mp3, jpg) appears as random noise. The most interesting files I've looked at so far are executables. A structured dataset might be interesting. There are probably a lot of visually interesting file formats that I haven't investigated yet; Blender files are worth looking at.

BinaryImageDisplay is the main class that runs the program. Its window is filled with an ImagePanel, which takes in an ArrayList of colors and arranges them as an image in a panel. The ArrayList of colors is created by ImageInterpreter, which reads a file of bits and groups them into colors.