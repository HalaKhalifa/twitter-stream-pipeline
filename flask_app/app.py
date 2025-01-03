from flask import Flask, render_template, request, jsonify
from elasticsearch import Elasticsearch

app = Flask(__name__)
es = Elasticsearch("http://localhost:9200")

@app.route("/")
def index():
    return render_template("index.html", message="Welcome to the Twitter Analytics Dashboard!")

if __name__ == "__main__":
    app.run(debug=True)