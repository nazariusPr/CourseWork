import moondream as md
from PIL import Image
from deep_translator import GoogleTranslator
from flask import Flask, request, jsonify

app = Flask(__name__)

model = md.vl(model="moondream-2b-int8.mf")


@app.route('/api/v1/analyze', methods=['POST'])
def analyze():
    if 'image' not in request.files:
        return jsonify({'error': 'No image part'}), 400

    image = request.files['image']

    if image.filename == '':
        return jsonify({'error': 'No selected file'}), 400

    processed_image = Image.open(image.stream)
    encoded_image = model.encode_image(processed_image)
    answer = model.query(encoded_image, 'Describe objects which are in this image ?')['answer']

    language = request.headers.get('Accept-Language', 'en')
    if language != 'en':
        answer = GoogleTranslator(source='en', target=language).translate(answer)

    return jsonify({
        'message': answer,
    }), 200


if __name__ == '__main__':
    app.run(debug=False, host='0.0.0.0', port=9090)
