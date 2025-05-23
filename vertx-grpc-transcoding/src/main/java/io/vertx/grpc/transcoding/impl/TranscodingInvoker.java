/*
 * Copyright (c) 2011-2025 Contributors to the Eclipse Foundation
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0, or the Apache License, Version 2.0
 * which is available at https://www.apache.org/licenses/LICENSE-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0 OR Apache-2.0
 */
package io.vertx.grpc.transcoding.impl;

import io.vertx.core.http.HttpServerRequest;
import io.vertx.grpc.common.ServiceMethod;
import io.vertx.grpc.server.impl.GrpcHttpInvoker;
import io.vertx.grpc.server.impl.GrpcInvocation;

public class TranscodingInvoker implements GrpcHttpInvoker {

  @Override
  public <Req, Resp> GrpcInvocation<Req, Resp> accept(HttpServerRequest request, ServiceMethod<Req, Resp> serviceMethod) {
    TranscodingServiceMethodImpl<Req, Resp> transcodingServiceMethod;

    if (serviceMethod instanceof TranscodingServiceMethodImpl) {
      transcodingServiceMethod = (TranscodingServiceMethodImpl<Req, Resp>) serviceMethod;
    } else {
      transcodingServiceMethod = new TranscodingServiceMethodImpl<>(
        serviceMethod.serviceName(),
        serviceMethod.methodName(),
        serviceMethod.encoder(),
        serviceMethod.decoder()
      );
    }

    return transcodingServiceMethod.accept(request);
  }
}
