import sys
import json
from textblob import TextBlob

def analyze_sentiment(tweet):
    blob = TextBlob(tweet)
    sentiment = blob.sentiment
    return {
        "sentiment": "positive" if sentiment.polarity > 0 else "negative" if sentiment.polarity < 0 else "neutral",
        "polarity": sentiment.polarity,
        "subjectivity": sentiment.subjectivity
    }

if __name__ == "__main__":
    tweet = sys.argv[1]  # Get tweet from command-line argument
    sentiment_result = analyze_sentiment(tweet)
    print(json.dumps(sentiment_result))
