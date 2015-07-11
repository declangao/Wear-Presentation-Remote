import os, platform
from flask import Flask, request

osp = platform.system()

app = Flask(__name__)
app.config.from_object(__name__)

app.config.update(dict(
	DEBUG=True
))


@app.route('/')
def send_key():
	key=request.args['key']

	if 'Darwin' ==  osp:
		send_key_mac(key)
		return 'Mac OS X'
	elif 'Linux' == osp:
		return 'Linux'
	elif 'Windows' == osp:
		return 'Windows'
	else:
		return 'Unknown OS'


def send_key_mac(key):
	cmd_test = """
	osascript -e 'tell application "System Events" to keystroke "m" using {command down}'
	"""
	cmd_back = """
	osascript -e 'tell application "System Events" to key code 126'
	"""
	cmd_next = """
	osascript -e 'tell application "System Events" to key code 125'
	"""

	cmd = ''
	if key == 'back':
		cmd = cmd_back
	elif key == 'next':
		cmd = cmd_next
	else:
		cmd = cmd_test

	print('Sending key ' + key)
	os.system(cmd)
	return "Done"


def send_key_linux(key):
	pass


def send_key_windows(key):
	pass


@app.route('/test')
def test():
	return '200'


if __name__ == '__main__':
	app.run(host='0.0.0.0')
