package learn.thangs.rest.api.auth

import io.vertx.core.Vertx
import io.vertx.core.buffer.Buffer
import io.vertx.ext.auth.PubSecKeyOptions
import io.vertx.ext.auth.jwt.JWTAuth
import io.vertx.ext.auth.jwt.JWTAuthOptions

object AuthHelper {
    fun getJWTAuthProvider(vertx: Vertx, publicKeyData: String, privateKeyData: String? = null): JWTAuth {
        val jwtAuthOptions = JWTAuthOptions()
            // public key to decrypt jwt
            .addPubSecKey(
                PubSecKeyOptions()
                    .setAlgorithm("RS256")
                    .setBuffer(
                        Buffer.buffer()
                            .appendString("-----BEGIN PUBLIC KEY-----\n")
                            .appendString(publicKeyData)
                            .appendString("\n-----END PUBLIC KEY-----")
                    )
            )
        if (privateKeyData != null) {

            // private key to encode jwt
            jwtAuthOptions
                .addPubSecKey(
                    PubSecKeyOptions()
                        .setAlgorithm("RS256")
                        .setBuffer(
                            Buffer.buffer()
                                .appendString("-----BEGIN PRIVATE KEY-----\n")
                                .appendString(privateKeyData)
                                .appendString("\n-----END PRIVATE KEY-----")
                        )
                )
        }
        return JWTAuth.create(
            vertx,
            jwtAuthOptions
        )
    }
}