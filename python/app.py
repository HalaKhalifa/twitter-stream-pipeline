from flask import Flask

app = Flask(__name__)

@app.route('/')
def home():
    return "Welcome to the Twitter Stream Pipeline API!"

@app.route('/dashboard')
def dashboard():
    return '''
        <iframe src="http://localhost:5601/goto/30e54f30-c991-11ef-9b22-2331ba214a04"
                width="100%" height="800px" frameborder="0">
        </iframe>
    '''

if __name__ == '__main__':
    app.run(debug=True)
