#version 150

#moj_import <fog.glsl>
#moj_import <kap_lib:extras.glsl>
#moj_import <kap_lib:noise3d.glsl>
const float GAME_TIME_SCALE = 200.0;

uniform sampler2D Sampler0;

uniform float GameTime;
uniform vec4 ChromaConfig;
uniform vec4 ColorModulator;
uniform float FogStart;
uniform float FogEnd;
uniform vec4 FogColor;

in float vertexDistance;
in vec4 vertexColor;
in vec2 texCoord0;
in vec4 normal;
in vec3 worldPos;

out vec4 fragColor;

void main() {
    vec4 color = texture(Sampler0, texCoord0) * vertexColor * ColorModulator;
    if (color.a < .1) discard;
    vec3 deltaPos = worldPos;
    deltaPos *= 16;
    deltaPos = vec3(floor(deltaPos.x), floor(deltaPos.y), floor(deltaPos.z)); //create the pixels
    deltaPos *= .125;
    deltaPos.y += GameTime * ChromaConfig.b * GAME_TIME_SCALE;
    float chroma = cnoise(deltaPos * .4);// * ChromaConfig.g;
    fragColor = linear_fog(
        vec4(hsb2rgb(vec3(chroma, 1.0, 1.0)) * color.rgb, color.a),
        vertexDistance, FogStart, FogEnd, FogColor
    );
}