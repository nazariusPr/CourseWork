import moondream as md
from PIL import Image
from deep_translator import GoogleTranslator
from flask import Flask, request, jsonify
import io

app = Flask(__name__)

model = md.vl(model="moondream-2b-int8.mf")


@app.route('/api/v1/analyze/multipart', methods=['POST'])
def analyze():
    if 'image' not in request.files:
        return jsonify({'error': 'No image part'}), 400

    image_file = request.files['image']

    if image_file.filename == '':
        return jsonify({'error': 'No selected file'}), 400

    try:
        image = Image.open(image_file.stream).convert('RGB')
    except Exception as e:
        return jsonify({'error': 'Failed to process image', 'details': str(e)}), 400

    return process(image, request.headers.get('Accept-Language', 'en'))


@app.route('/api/v1/analyze/bytes', methods=['POST'])
def analyze_bytes():
    image_bytes = request.data

    if not image_bytes:
        return jsonify({'error': 'No image data provided'}), 400

    try:
        image = Image.open(io.BytesIO(image_bytes))
    except Exception as e:
        return jsonify({'error': 'Failed to process image', 'details': str(e)}), 400

    return process(image, request.headers.get('Accept-Language', 'en'))


def process(image, language):
    encoded_image = model.encode_image(image)
    answer = model.query(encoded_image, 'Describe the objects in the image. What are they?')['answer']

    if language != 'en':
        answer = GoogleTranslator(source='en', target=language).translate(answer)

    return jsonify({
        'message': answer,
    }), 200


if __name__ == '__main__':
    app.run(debug=False, host='0.0.0.0', port=9090)
