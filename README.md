# Android Wear Presentation Remote
Use your Android Wear smartwatch to control presentation!

## About
The mobile app sends a notification to connected Wear device. User can open up the notification and use notification actions to control the presentation. 

There is no Wear app at the moment. Everything is done via notification. The drawback is obvious. When the screen timeout on your watch kicks in, you have to open up the notification again. I **will** implement a Wear app in the future to do it properly.

Server application is required to run on the desktop. It's written in Python and very robust. And it currently supports Mac OS X only.

## Usage
Run server.py on your desktop computer before trying to connect on your mobile. 

```
python server.py
```
Make sure you have flask framework installed. If not, run `pip install flask`.

With any luck, you should now be able to use "Next" and "Back" buttons via notification on your watch.

## Screenshots

![Wear Notification](http://i.imgur.com/wn0Mn1y.png)
![Next](http://i.imgur.com/oTN2T5W.png)
![Back](http://i.imgur.com/Ajdabc4.png)
![Mobile app](http://i.imgur.com/rwMUwh5.png)


## Todo
1. Implement a proper Android Wear app to communicate with the mobile app using MessageApi
2. Add Windows and Linux support

## License
Copyright [DeclanGao](http://twitter.com/DeclanGao/) © 2015.

Licensed under GPL v3 License.