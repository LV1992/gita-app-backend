package com.gita.backend.controller;

import com.gita.backend.configuartion.Receiver;
import com.gita.backend.enums.WebExceptions;
import com.gita.backend.exceptions.BusinessException;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.UUID;

/**
 * 公用控制器
 *
 * @author yihang.lv 2018/9/6、15:13
 */
@Controller
@RequestMapping("common")
public class CommonController {

    @Autowired
    private Receiver receiver;

    @Autowired
    private Jedis jedis;


    @Value("${server.port}")
    private int serverPort;

    /**
     * 获取二维码
     *
     * @return
     */
    @GetMapping("getQrCode")
    @ResponseBody
    public Map<String, Object> getQrCode() {
        Map<String, Object> result = new HashMap<>();
        String sessionKey = "PC:" + UUID.randomUUID().toString();
        result.put("sessionKey", sessionKey);

        // app端登录地址
        String loginUrl = "http://localhost:"+serverPort+"/user/qrUrl/";
        result.put("loginUrl", loginUrl);
        String qrCode = "";
        try {
            qrCode = createQrCode(loginUrl);
        } catch (Exception e) {
            throw new BusinessException(WebExceptions.BUILDING_QR_CODE_ERROR.getCode(),WebExceptions.BUILDING_QR_CODE_ERROR.getMsg());
        }
        result.put("image", qrCode);
        jedis.setex(sessionKey, 5 ,sessionKey);
        return result;
    }

    /**
     * 生成base64二维码
     *
     * @param content
     * @return
     * @throws Exception
     */
    private String createQrCode(String content) throws Exception{
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            hints.put(EncodeHintType.MARGIN, 1);
            BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, 400, 400, hints);
            int width = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
                }
            }
            ImageIO.write(image, "JPG", out);
            return Base64.encodeBase64String(out.toByteArray());
        }
    }

}
