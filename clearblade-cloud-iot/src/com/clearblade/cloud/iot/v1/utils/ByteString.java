/*
 * Copyright 2023 ClearBlade Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Copyright 2022 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.clearblade.cloud.iot.v1.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FilterOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.List;


public class ByteString {

	private final byte[] bytes;

	private ByteString(final byte[] bytes) {
		this.bytes = bytes;
	}
	
	public ByteString(String str) {
		this.bytes = str.getBytes();
	}

	/**
	 * Gets the byte at the given index.
	 *
	 * @throws ArrayIndexOutOfBoundsException {@code index} is < 0 or >= size
	 */
	public byte byteAt(final int index) {
		return bytes[index];
	}

	/**
	 * Gets the number of bytes.
	 */
	public int size() {
		return bytes.length;
	}

	/**
	 * Returns {@code true} if the size is {@code 0}, {@code false} otherwise.
	 */
	public boolean isEmpty() {
		return bytes.length == 0;
	}

	// =================================================================
	// byte[] -> ByteString
	/**
	 * Empty ByteString.
	 */
	public static final ByteString EMPTY = new ByteString(new byte[0]);

	/**
	 * Copies the given bytes into a {@code ByteString}.
	 */
	public static ByteString copyFrom(final byte[] bytes, final int offset, final int size) {
		final byte[] copy = new byte[size];
		System.arraycopy(bytes, offset, copy, 0, size);
		return new ByteString(copy);
	}

	/**
	 * Copies the given bytes into a {@code ByteString}.
	 */
	public static ByteString copyFrom(final byte[] bytes) {
		return copyFrom(bytes, 0, bytes.length);
	}

	/**
	 * Copies {@code size} bytes from a {@code java.nio.ByteBuffer} into a
	 * {@code ByteString}.
	 */
	public static ByteString copyFrom(final ByteBuffer bytes, final int size) {
		final byte[] copy = new byte[size];
		bytes.get(copy);
		return new ByteString(copy);
	}

	/**
	 * Copies the remaining bytes from a {@code java.nio.ByteBuffer} into a
	 * {@code ByteString}.
	 */
	public static ByteString copyFrom(final ByteBuffer bytes) {
		return copyFrom(bytes, bytes.remaining());
	}

	/**
	 * Encodes {@code text} into a sequence of bytes using the named charset and
	 * returns the result as a {@code ByteString}.
	 */
	public static ByteString copyFrom(final String text, final String charsetName) throws UnsupportedEncodingException {
		return new ByteString(text.getBytes(charsetName));
	}

	/**
	 * Encodes {@code text} into a sequence of UTF-8 bytes and returns the result as
	 * a {@code ByteString}.
	 */
	public static ByteString copyFromUtf8(final String text) {
		try {
			return new ByteString(text.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("UTF-8 not supported?", e);
		}
	}

	/**
	 * Concatenates all byte strings in the list and returns the result.
	 *
	 * <p>
	 * The returned {@code ByteString} is not necessarily a unique object. If the
	 * list is empty, the returned object is the singleton empty {@code ByteString}.
	 * If the list has only one element, that {@code ByteString} will be returned
	 * without copying.
	 */
	public static ByteString copyFrom(List<ByteString> list) {
		if (list.size() == 0) {
			return EMPTY;
		} else if (list.size() == 1) {
			return list.get(0);
		}
		int size = 0;
		for (ByteString str : list) {
			size += str.size();
		}
		byte[] bytes = new byte[size];
		int pos = 0;
		for (ByteString str : list) {
			System.arraycopy(str.bytes, 0, bytes, pos, str.size());
			pos += str.size();
		}
		return new ByteString(bytes);
	}

	// =================================================================
	// ByteString -> byte[]
	/**
	 * Copies bytes into a buffer at the given offset.
	 *
	 * @param target buffer to copy into
	 * @param offset in the target buffer
	 */
	public void copyTo(final byte[] target, final int offset) {
		System.arraycopy(bytes, 0, target, offset, bytes.length);
	}

	/**
	 * Copies bytes into a buffer.
	 *
	 * @param target       buffer to copy into
	 * @param sourceOffset offset within these bytes
	 * @param targetOffset offset within the target buffer
	 * @param size         number of bytes to copy
	 */
	public void copyTo(final byte[] target, final int sourceOffset, final int targetOffset, final int size) {
		System.arraycopy(bytes, sourceOffset, target, targetOffset, size);
	}

	/**
	 * Copies bytes to a {@code byte[]}.
	 */
	public byte[] toByteArray() {
		final int size = bytes.length;
		final byte[] copy = new byte[size];
		System.arraycopy(bytes, 0, copy, 0, size);
		return copy;
	}

	/**
	 * Constructs a new read-only {@code java.nio.ByteBuffer} with the same backing
	 * byte array.
	 */
	public ByteBuffer asReadOnlyByteBuffer() {
		final ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
		return byteBuffer.asReadOnlyBuffer();
	}

	/**
	 * Constructs a new {@code String} by decoding the bytes using the specified
	 * charset.
	 */
	public String toString(final String charsetName) throws UnsupportedEncodingException {
		return new String(bytes, charsetName);
	}

	/**
	 * Constructs a new {@code String} by decoding the bytes as UTF-8.
	 */
	public String toStringUtf8() {
		try {
			return new String(bytes, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("UTF-8 not supported?", e);
		}
	}

	// =================================================================
	// equals() and hashCode()
	@Override
	public boolean equals(final Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof ByteString)) {
			return false;
		}
		final ByteString other = (ByteString) o;
		final int size = bytes.length;
		if (size != other.bytes.length) {
			return false;
		}
		final byte[] thisBytes = bytes;
		final byte[] otherBytes = other.bytes;
		for (int i = 0; i < size; i++) {
			if (thisBytes[i] != otherBytes[i]) {
				return false;
			}
		}
		return true;
	}

	private volatile int hash = 0;

	@Override
	public int hashCode() {
		int h = hash;
		if (h == 0) {
			final byte[] thisBytes = bytes;
			final int size = bytes.length;
			h = size;
			for (int i = 0; i < size; i++) {
				h = h * 31 + thisBytes[i];
			}
			if (h == 0) {
				h = 1;
			}
			hash = h;
		}
		return h;
	}

	// =================================================================
	// Input stream
	/**
	 * Creates an {@code InputStream} which can be used to read the bytes.
	 */
	public InputStream newInput() {
		return new ByteArrayInputStream(bytes);
	}

	/**
	 * Creates a {@link CodedInputStream} which can be used to read the bytes. Using
	 * this is more efficient than creating a {@link CodedInputStream} wrapping the
	 * result of {@link #newInput()}.
	 */
	// =================================================================
	// Output stream
	/**
	 * Creates a new {@link Output} with the given initial capacity.
	 */
	public static Output newOutput(final int initialCapacity) {
		return new Output(new ByteArrayOutputStream(initialCapacity));
	}

	/**
	 * Creates a new {@link Output}.
	 */
	public static Output newOutput() {
		return newOutput(32);
	}

	/**
	 * Outputs to a {@code ByteString} instance. Call {@link #toByteString()} to
	 * create the {@code ByteString} instance.
	 */
	public static final class Output extends FilterOutputStream {
		private final ByteArrayOutputStream bout;

		/**
		 * Constructs a new output with the given initial capacity.
		 */
		private Output(final ByteArrayOutputStream bout) {
			super(bout);
			this.bout = bout;
		}

		/**
		 * Creates a {@code ByteString} instance from this {@code Output}.
		 */
		public ByteString toByteString() {
			final byte[] byteArray = bout.toByteArray();
			return new ByteString(byteArray);
		}
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return toStringUtf8();
	}

}
